package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.User

class APILocalBan(token: String, reason: String, target: User, punisher: User): APIRequest(
    "/ban",
    "POST",
    BodyBuilder().setJSON(
        JSONObject()
            .append("token", token)
            .append("reason", reason)
            .append("target", target.uuid.toString().replace("-", "")) // todo: change to user id?
            .append("punisher", punisher.uuid.toString().replace("-", ""))
    ).build()
)
