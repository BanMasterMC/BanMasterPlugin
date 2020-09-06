package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import java.util.UUID

class APIUnban(token: String, player: UUID, unbanner: UUID): APIRequest(
        "unban",
        "POST",
        BodyBuilder().setJSON(JSONObject().append("token", token).append("uuid", player.toString().replace("-", "")).append("unbanner", unbanner.toString().replace("-", ""))).build(),
)
