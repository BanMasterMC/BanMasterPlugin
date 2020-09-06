package pro.banmaster.api.rest.free.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.noHyphens
import java.util.UUID

class APIPlayerGlobalBanList(uuid: UUID): APIRequest( // todo
    "/free/banlist/player",
    "POST",
    BodyBuilder().setJSON(JSONObject().append("uuid", uuid.noHyphens())).build(),
)
