package pro.banmaster.api.rest.response

import org.json.JSONObject
import pro.banmaster.api.struct.Ban
import pro.banmaster.api.struct.getBoolean
import pro.banmaster.api.struct.preprocessResponse
import util.JSONAPI

class APIIsBannedResponse(response: JSONAPI.Response<JSONObject>) {
    val isBanned: Boolean
    val banData: Ban?
    val strike: Boolean

    init {
        preprocessResponse(response)
        val data = response.response.getJSONObject("DATA")
        isBanned = data.getBoolean("ISBANNED", false)
        strike = data.getBoolean("STRIKE", false)
        banData = if (isBanned) Ban.parse(data.getJSONObject("BANDATA")) else null
    }
}
