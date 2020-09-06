package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import java.util.*

class APIIsBanned(token: String, uuid: UUID): APIRequest(
    "/isbanned",
    "POST",
    BodyBuilder().setJSON(JSONObject().append("token", token).append("uuid", uuid.toString().replace("-", ""))).build()
)
