package pro.banmaster.api.rest

import org.json.JSONObject
import util.JSONAPI

abstract class APIRequest: JSONAPI {
    constructor(path: String, method: String, body: RequestBody) : super(ENDPOINT + path, method, body)

    constructor(path: String, method: String) : super(path, method)

    companion object {
        const val ENDPOINT = "https://api.banmaster.pro/v1"
    }

    fun execute(): Response<JSONObject> = call(JSONObject::class.java)
}