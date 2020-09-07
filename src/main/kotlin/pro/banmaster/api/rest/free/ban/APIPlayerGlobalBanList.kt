package pro.banmaster.api.rest.free.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.noHyphens
import java.util.UUID

class APIPlayerGlobalBanList(uuid: UUID): APIRequest( // todo: check whether api exists before using this api
    "/free/banlist/player",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("uuid", uuid.noHyphens())).build(),
)
