package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.Ban
import pro.banmaster.api.struct.noHyphens
import java.util.UUID

class APIGlobalBan(token: String, reason: String, target: UUID, punisher: UUID): APIRequest(
    "/gban",
    "POST",
    BodyBuilder().setJSON(
        JSONObject()
            .append("token", token)
            .append("reason", reason)
            .append("target", target.noHyphens()) // todo: change to user id?
            .append("punisher", punisher.noHyphens())
    ).build()
) {
    override fun execute(): Ban = Ban.parseResponse(executeAPI())
}
