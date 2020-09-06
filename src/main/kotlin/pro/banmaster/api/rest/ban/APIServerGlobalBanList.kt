package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest

class APIServerGlobalBanList(address: String): APIRequest(
        "/free/banlist/server",
        "POST",
        BodyBuilder().setJSON(JSONObject().append("address", address)).build(),
)
