package pro.banmaster.api.rest.response

import org.json.JSONObject
import pro.banmaster.api.struct.Ban
import pro.banmaster.api.struct.getBoolean
import pro.banmaster.api.struct.preprocessResponse
import pro.banmaster.api.rest.JSONAPI2

class APIJoinResponse(response: JSONAPI2.Response<JSONObject>) {
    val isBanned: Boolean
    val banData: Ban?

    init {
        preprocessResponse(response)
        val data = response.response.getJSONObject("DATA")
        isBanned = data.getBoolean("ISBANNED", false)
        banData = if (isBanned) Ban.parse(data.getJSONObject("BANDATA")) else null
    }
}
