package pro.banmaster.api.rest

import org.json.JSONObject
import pro.banmaster.api.struct.preprocessResponse
import pro.banmaster.bukkit.BanMasterPlugin

abstract class APIRequest: JSONAPI2 {
    private val body: RequestBody?

    constructor(path: String, method: String, body: RequestBody) : super(ENDPOINT + path, method, body) {
        this.body = body
    }

    constructor(path: String, method: String) : super(ENDPOINT + path, method) {
        this.body = null
    }

    companion object {
        const val ENDPOINT = "https://api.banmaster.dev/v1"
    }

    protected fun executeAPI(): Response<JSONObject> {
        if (BanMasterPlugin.debug) {
            val sugar = if (body == null) "" else ", body: " + body.rawBody
            BanMasterPlugin.log.info("[debug -> API] Executing $url (method: $method$sugar)")
        }
        setPostConnection {
            it.setHeader("Content-Type", "application/json")
            it.setHeader("Accept", "application/json")
        }
        val res = call(JSONObject::class.java)
        if (BanMasterPlugin.debug) BanMasterPlugin.log.info("[debug <- API] Response from $url: ${res.rawResponse}")
        return res
    }

    open fun execute(): Any = preprocessResponse(call(JSONObject::class.java))
}
