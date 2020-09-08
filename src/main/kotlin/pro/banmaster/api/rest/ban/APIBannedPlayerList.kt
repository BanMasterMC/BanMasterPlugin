package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.SimpleUUIDList

class APIBannedPlayerList(token: String): APIRequest(
    "/getbanplayers",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token)).build(),
) {
    override fun execute(): SimpleUUIDList = SimpleUUIDList.parse(executeAPI())
}
