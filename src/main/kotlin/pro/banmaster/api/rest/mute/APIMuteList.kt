package pro.banmaster.api.rest.mute

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.SimpleList
import pro.banmaster.api.struct.Mute
import pro.banmaster.api.struct.noHyphens
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import java.util.*

class APIMuteList(uuid: UUID, limit: Int, offset: Int): APIRequest(
    "/mutelist",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", uuid.noHyphens()).put("limit", limit).put("offset", offset)).build(),
) {
    override fun execute(): SimpleList<Mute>? = try {
        SimpleList.parse(executeAPI()) { Mute.parse(it as JSONObject) }
    } catch (e: RuntimeException) { null }
}
