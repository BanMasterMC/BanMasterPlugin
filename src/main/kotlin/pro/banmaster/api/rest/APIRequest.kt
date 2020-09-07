package pro.banmaster.api.rest

import org.json.JSONObject
import pro.banmaster.api.struct.preprocessResponse
import pro.banmaster.bukkit.BanMasterPlugin
import util.JSONAPI

abstract class APIRequest: JSONAPI {
    private val url: String
    private val method: String
    private val body: RequestBody?

    constructor(path: String, method: String, body: RequestBody) : super(ENDPOINT + path, method, body) {
        this.url = ENDPOINT + path
        this.method = method
        this.body = body
    }

    constructor(path: String, method: String) : super(ENDPOINT + path, method) {
        this.url = ENDPOINT + path
        this.method = method
        this.body = null
    }

    companion object {
        const val ENDPOINT = "https://api.banmaster.dev/v1"
    }

    protected fun executeAPI(): Response<JSONObject> {
        if (BanMasterPlugin.debug) {
            val sugar = if (body == null) "" else ", body: " + RequestBody::class.java.getDeclaredField("rawBody").apply { isAccessible = true }.get(body)
            BanMasterPlugin.log.info("[debug -> API] Executing $url (method: $method$sugar)")
        }
        setPostConnection {
            it.doOutput = true
            it.setRequestProperty("Content-Type", "application/json")
            it.setRequestProperty("Accept", "application/json")
        }
        val res = call(JSONObject::class.java)
        if (BanMasterPlugin.debug) BanMasterPlugin.log.info("[debug <- API] Response from $url: ${if (res.response == null) "Raw: ${res.rawResponse}" else res.response.toString()}")
        return res
    }

    open fun execute(): Any = preprocessResponse(executeAPI())
}
