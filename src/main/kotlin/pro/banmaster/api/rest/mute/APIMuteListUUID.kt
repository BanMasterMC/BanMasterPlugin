package pro.banmaster.api.rest.mute

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.SimpleUUIDList
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token

class APIMuteListUUID: APIRequest(
    "/getmuteplayers",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token)).build(),
) {
    override fun execute(): SimpleUUIDList = SimpleUUIDList.parse(executeAPI())
}
