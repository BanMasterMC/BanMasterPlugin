package pro.banmaster.api.rest.misc

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.APIJoinResponse
import pro.banmaster.api.struct.noHyphens
import java.util.*

class APIJoin(uuid: UUID, ip: String): APIRequest(
    "/join",
    "POST",
    BodyBuilder().setJSON(JSONObject().append("uuid", uuid.noHyphens()).append("ip", ip)).build()
) {
    override fun execute(): APIJoinResponse = APIJoinResponse(executeAPI())
}
