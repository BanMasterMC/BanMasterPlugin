package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import java.util.UUID

class APILocalBan(token: String, reason: String, target: UUID, punisher: UUID): APIRequest(
    "/ban",
    "POST",
    BodyBuilder().setJSON(
        JSONObject()
            .append("token", token)
            .append("reason", reason)
            .append("target", target.toString().replace("-", "")) // todo: change to user id?
            .append("punisher", punisher.toString().replace("-", ""))
    ).build()
)
