package pro.banmaster.api.rest.warn

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.Warn
import pro.banmaster.api.struct.noHyphens
import java.util.UUID

class APIWarn(token: String, reason: String, target: UUID, punisher: UUID): APIRequest(
    "/warn",
    "POST",
    BodyBuilder().setJSON(
        JSONObject()
            .put("token", token)
            .put("reason", reason)
            .put("target", target.noHyphens())
            .put("punisher", punisher.noHyphens())
    ).build()
) {
    override fun execute(): Warn = Warn.parseResponse(super.executeAPI())
}
