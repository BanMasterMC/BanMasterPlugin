package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.APIIsBannedResponse
import pro.banmaster.api.struct.noHyphens
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import util.promise.Promise
import java.util.*

class APIIsBanned(uuid: UUID): APIRequest(
    "/isbanned",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", uuid.noHyphens())).build()
) {
    override fun execute(): Promise<APIIsBannedResponse> = Promise.async { APIIsBannedResponse(executeAPI()) }
}
