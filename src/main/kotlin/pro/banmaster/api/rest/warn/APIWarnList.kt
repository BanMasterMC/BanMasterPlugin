package pro.banmaster.api.rest.warn

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.SimpleUUIDList

class APIWarnList(token: String): APIRequest(
    "/getwarnplayers",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token)).build(),
) {
    override fun execute(): SimpleUUIDList = SimpleUUIDList.parse(executeAPI())
}
