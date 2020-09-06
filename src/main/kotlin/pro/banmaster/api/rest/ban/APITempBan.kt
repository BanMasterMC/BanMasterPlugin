package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.Ban
import pro.banmaster.api.struct.noHyphens
import java.util.UUID

class APITempBan(token: String, reason: String, target: UUID, punisher: UUID, expires: Long): APIRequest(
    "/tban",
    "POST",
    BodyBuilder().setJSON(
        JSONObject()
            .append("token", token)
            .append("reason", reason)
            .append("target", target.noHyphens()) // todo: change to user id?
            .append("punisher", punisher.noHyphens())
            .append("expires", expires),
    ).build()
) {
    override fun execute(): Ban = Ban.parseResponse(executeAPI())
}
