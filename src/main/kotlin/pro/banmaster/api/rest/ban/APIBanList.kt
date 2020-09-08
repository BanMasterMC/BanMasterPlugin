package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.SimpleList
import pro.banmaster.api.struct.Ban
import pro.banmaster.api.struct.noHyphens
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import java.util.*

class APIBanList(uuid: UUID, limit: Int, offset: Int): APIRequest(
    "/banlist",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", uuid.noHyphens()).put("limit", limit).put("offset", offset)).build(),
) {
    override fun execute(): SimpleList<Ban>? = try {
        SimpleList.parse(executeAPI()) { Ban.parse(it as JSONObject) }
    } catch (e: RuntimeException) { null }
}
