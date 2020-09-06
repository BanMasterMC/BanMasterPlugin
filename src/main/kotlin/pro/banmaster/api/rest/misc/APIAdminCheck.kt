package pro.banmaster.api.rest.misc

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.BooleanResponse
import pro.banmaster.api.struct.noHyphens
import util.promise.Promise
import java.util.UUID

class APIAdminCheck(token: String, uuid: UUID): APIRequest(
    "/isAdmin",
    "POST",
    BodyBuilder().setJSON(JSONObject().append("token", token).append("uuid", uuid.noHyphens())).build(),
) {
    override fun execute(): Promise<BooleanResponse> = Promise.async { BooleanResponse(executeAPI()) }
}
