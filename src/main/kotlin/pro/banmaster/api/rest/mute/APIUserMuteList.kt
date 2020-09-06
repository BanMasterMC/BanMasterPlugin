package pro.banmaster.api.rest.mute

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import java.util.UUID

class APIUserMuteList(uuid: UUID): APIRequest(
        "/free/mutelist/user",
        "POST",
        BodyBuilder().setJSON(JSONObject().append("uuid", uuid.toString().replace("-", ""))).build(),
)
