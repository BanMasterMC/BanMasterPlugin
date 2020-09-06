package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import java.util.UUID

class APIPlayerGlobalBanList(uuid: UUID): APIRequest(
        "/free/banlist/player",
        "POST",
        BodyBuilder().setJSON(JSONObject().append("uuid", uuid.toString().replace("-", ""))).build(),
)
