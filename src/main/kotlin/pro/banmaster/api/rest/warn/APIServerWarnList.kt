package pro.banmaster.api.rest.warn

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest

class APIServerWarnList(token: String): APIRequest(
        "/warnlist",
        "POST",
        BodyBuilder().setJSON(JSONObject().append("token", token)).build()
)
