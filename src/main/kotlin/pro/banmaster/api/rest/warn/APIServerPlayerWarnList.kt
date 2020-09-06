package pro.banmaster.api.rest.warn

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import java.util.UUID

class APIServerPlayerWarnList(token: String, uuid: UUID): APIRequest(
        "/warnlist/player",
        "POST",
        BodyBuilder().setJSON(JSONObject().append("token", token).append("uuid", uuid.toString().replace("-", ""))).build()
)
