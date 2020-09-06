package pro.banmaster.api.rest.warn

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.SimpleUUIDList
import pro.banmaster.api.struct.noHyphens
import java.util.UUID

class APIServerPlayerWarnList(token: String, uuid: UUID): APIRequest(
    "/warnlist/player",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", uuid.noHyphens())).build()
) {
    override fun execute(): SimpleUUIDList = SimpleUUIDList.parse(executeAPI())
}
