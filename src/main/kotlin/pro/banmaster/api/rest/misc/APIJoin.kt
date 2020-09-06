package pro.banmaster.api.rest.misc

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import java.util.*

class APIJoin(uuid: UUID, ip: String): APIRequest(
    "/join",
    "POST",
    BodyBuilder().setJSON(JSONObject().append("uuid", uuid.toString().replace("-", "")).append("ip", ip)).build()
)
