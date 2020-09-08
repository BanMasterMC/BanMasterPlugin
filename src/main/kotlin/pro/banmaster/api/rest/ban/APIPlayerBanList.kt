package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.noHyphens
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import java.util.*

class APIPlayerBanList(uuid: UUID, limit: Int, offset: Int): APIRequest( // todo
    "/banlist/player",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", uuid.noHyphens()).put("limit", limit).put("offset", offset)).build()
)
