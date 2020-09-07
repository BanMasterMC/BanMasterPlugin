package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.noHyphens
import java.util.UUID

class APIUnban(token: String, player: UUID, unbanner: UUID): APIRequest(
    "/unban",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", player.noHyphens()).put("unbanner", unbanner.noHyphens())).build(),
)
// responses should be ignored
