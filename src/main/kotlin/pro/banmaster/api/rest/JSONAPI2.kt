package pro.banmaster.api.rest

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import util.EventEmitter
import util.Thrower
import java.io.IOException
import java.net.URL

open class JSONAPI2(url: String) : EventEmitter() {
    val url: URL = Thrower.invoke { URL(url) }
    var method = "GET"
    var requestBody: RequestBody? = null
    private var postConnection = { _: HttpRequestBase -> }

    constructor(url: String, method: String) : this(url) {
        this.method = method
    }

    constructor(url: String, method: String, requestBody: RequestBody) : this(url, method) {
        this.requestBody = requestBody
    }

    constructor(url: String, method: String, bodyBuilder: BodyBuilder) : this(url, method, bodyBuilder.build()) {}

    fun setPostConnection(consumer: (HttpRequestBase) -> Unit): JSONAPI2 {
        postConnection = consumer
        return this
    }

    /**
     * This method will try to cast to JSONObject.<br></br>
     * Following events will be emitted during this call:<br></br>
     *
     *  * postConnection - before connect
     *  * connection - after connection
     *
     * @see .call
     */
    fun call(): Response<JSONObject> {
        return call(JSONObject::class.java)
    }

    /**
     * Following events will be emitted during this call:<br></br>
     *
     *  * postConnection - before connect
     *  * connection - after connection
     *
     * jsonClass must have string constructor to deserialize the json.
     * @see .call
     */
    fun <T> call(jsonClass: Class<T>): Response<T> {
        try {
            val client = HttpClientBuilder.create().build()
            val http = if (method == "POST") HttpPost(url.toURI()) else HttpGet(url.toURI())
            postConnection.invoke(http)
            emit("postConnection", http)
            if (requestBody != null && requestBody!!.map.size != 0) requestBody!!.map.forEach { (key: String?, value: String?) -> http.setHeader(key, value) }
            if (requestBody != null && requestBody!!.rawBody != null) {
                val entity = StringEntity(requestBody!!.rawBody!!)
                if (http is HttpPost) http.entity = entity
            }
            val response = client.execute(http)
            emit("connection", client)
            val output = EntityUtils.toString(response.entity)
            return try {
                Response(response.statusLine.statusCode, jsonClass.getConstructor(String::class.java).newInstance(output), output)
            } catch (e: JSONException) {
                Response(response.statusLine.statusCode, jsonClass.newInstance(), output)
            } catch (ignored: ReflectiveOperationException) {
                Response(response.statusLine.statusCode, jsonClass.newInstance(), output)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    class Response<T>(val responseCode: Int, val response: T, val rawResponse: String)
    class BodyBuilder {
        private val properties = HashMap<String, String>()
        private var rawBody: String? = null
        fun addRequestProperty(key: String, value: String): BodyBuilder {
            properties[key] = value
            return this
        }

        /**
         * Sets json object as body. This method conflicts with [BodyBuilder.setJSON] and [BodyBuilder.setRawBody].
         */
        fun setJSON(json: JSONObject): BodyBuilder {
            properties["Content-Type"] = "application/json"
            rawBody = json.toString()
            return this
        }

        /**
         * Sets json array as body. This method conflicts with [BodyBuilder.setJSON] and [BodyBuilder.setRawBody].
         */
        fun setJSON(json: JSONArray): BodyBuilder {
            properties["Content-Type"] = "application/json"
            rawBody = json.toString()
            return this
        }

        /**
         * Sets json object as body. This method conflicts with [BodyBuilder.setJSON] and [BodyBuilder.setJSON].
         */
        fun setRawBody(body: String?): BodyBuilder {
            properties.remove("Content-Type")
            rawBody = body
            return this
        }

        fun build(): RequestBody {
            return RequestBody(properties, rawBody)
        }
    }

    class RequestBody(val map: HashMap<String, String>, val rawBody: String?)
}
