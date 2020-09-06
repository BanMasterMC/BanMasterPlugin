package pro.banmaster.api.rest.response

import org.json.JSONObject
import pro.banmaster.api.struct.getBoolean
import pro.banmaster.api.struct.preprocessResponse
import pro.banmaster.api.rest.JSONAPI2

class BooleanResponse(response: JSONAPI2.Response<JSONObject>) {
    val data: Boolean

    init {
        preprocessResponse(response)
        data = response.response.getBoolean("DATA", false)
    }
}