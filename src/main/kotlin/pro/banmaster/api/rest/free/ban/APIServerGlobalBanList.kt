package pro.banmaster.api.rest.free.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest

class APIServerGlobalBanList(address: String): APIRequest( // todo: check whether api exists before using this api
    "/free/banlist/server",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("address", address)).build(),
)
