package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.APIIsBannedResponse
import pro.banmaster.api.struct.noHyphens
import util.promise.Promise
import java.util.*

class APIIsBanned(token: String, uuid: UUID, ip: String): APIRequest(
    "/isbanned",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", uuid.noHyphens()).build()
) {
    override fun execute(): Promise<APIIsBannedResponse> = Promise.async { APIIsBannedResponse(executeAPI()) }
}
