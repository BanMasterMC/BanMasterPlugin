package pro.banmaster.api.rest.response

import org.json.JSONObject
import pro.banmaster.api.struct.getBoolean
import pro.banmaster.api.struct.preprocessResponse
import util.JSONAPI

class BooleanResponse(response: JSONAPI.Response<JSONObject>) {
    val data: Boolean

    init {
        preprocessResponse(response)
        data = response.response.getBoolean("DATA", false)
    }
}