package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.noHyphens
import java.util.*

class APIIsBanned(token: String, uuid: UUID): APIRequest( // todo
    "/isbanned",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", uuid.noHyphens())).build()
)
