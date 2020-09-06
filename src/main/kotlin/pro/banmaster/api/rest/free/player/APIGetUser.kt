package pro.banmaster.api.rest.free.player

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.User
import pro.banmaster.api.struct.noHyphens
import util.promise.Promise
import java.util.UUID

class APIGetUser(uuid: UUID): APIRequest(
    "/free/user",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("player", uuid.noHyphens())).build(),
) {
    override fun execute(): Promise<User> = Promise.async { User.parseResponse(executeAPI()) }
}
