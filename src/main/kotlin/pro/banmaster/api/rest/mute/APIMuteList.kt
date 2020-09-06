package pro.banmaster.api.rest.mute

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest

class APIMuteList(token: String): APIRequest(
        "mutelist",
        "POST",
        BodyBuilder().setJSON(JSONObject().append("token", token)).build(),
)
