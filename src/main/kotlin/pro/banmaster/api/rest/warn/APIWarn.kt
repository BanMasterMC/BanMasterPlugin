package pro.banmaster.api.rest.warn

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import java.util.UUID

class APIWarn(token: String, reason: String, target: UUID, punisher: UUID): APIRequest(
        "/warn",
        "POST",
        BodyBuilder().setJSON(
                JSONObject()
                        .append("token", token)
                        .append("reason", reason)
                        .append("target", target.toString().replace("-", ""))
                        .append("punisher", punisher.toString().replace("-", ""))
        ).build()
)
