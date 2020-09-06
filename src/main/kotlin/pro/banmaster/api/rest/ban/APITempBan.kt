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
            .put("token", token)
            .put("reason", reason)
            .put("target", target.noHyphens()) // todo: change to user id?
            .put("punisher", punisher.noHyphens())
            .put("expires", (expires/1000F).toLong()),
    ).build()
) {
    override fun execute(): Ban = Ban.parseResponse(executeAPI())
}
