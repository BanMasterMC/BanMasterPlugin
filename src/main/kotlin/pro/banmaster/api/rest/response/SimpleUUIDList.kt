package pro.banmaster.api.rest.response

import org.json.JSONObject
import pro.banmaster.api.struct.preprocessResponse
import pro.banmaster.api.struct.toUUID
import util.JSONAPI
import java.util.UUID

class SimpleUUIDList: ArrayList<UUID>() {
    companion object {
        fun parse(response: JSONAPI.Response<JSONObject>): SimpleUUIDList {
            preprocessResponse(response)
            val list = SimpleUUIDList()
            response.response.getJSONArray("DATA").forEach { list.add((it as String).toUUID()) }
            return list
        }
    }
}
