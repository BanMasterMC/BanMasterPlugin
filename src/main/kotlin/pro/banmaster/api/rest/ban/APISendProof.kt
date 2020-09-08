package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.noHyphens
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import java.util.*

class APISendProof(uuid: UUID, url: String): APIRequest(
    "/sendproof",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("token", token).put("uuid", uuid.noHyphens()).put("url", url)).build(),
)
