package pro.banmaster.api.rest.warn

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.User

class APIWarn(token: String, reason: String, target: User, punisher: User): APIRequest(
        "/warn",
        "POST",
        BodyBuilder().setJSON(
                JSONObject()
                        .append("token", token)
                        .append("reason", reason)
                        .append("target", target.uuid.toString().replace("-", ""))
                        .append("punisher", punisher.uuid.toString().replace("-", ""))
        ).build()
)
