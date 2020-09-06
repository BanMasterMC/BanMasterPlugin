package pro.banmaster.api.rest.free.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest

class APIServerGlobalBanList(address: String): APIRequest( // todo
    "/free/banlist/server",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("address", address)).build(),
)
