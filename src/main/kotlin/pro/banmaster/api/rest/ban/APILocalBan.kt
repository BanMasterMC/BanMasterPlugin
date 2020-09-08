package pro.banmaster.api.rest.ban

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.Ban
import pro.banmaster.api.struct.noHyphens
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import java.util.UUID

class APILocalBan(reason: String, target: UUID, punisher: UUID): APIRequest(
    "/ban",
    "POST",
    BodyBuilder().setJSON(
        JSONObject()
            .put("token", token)
            .put("reason", reason)
            .put("target", target.noHyphens())
            .put("punisher", punisher.noHyphens())
    ).build()
) {
    override fun execute(): Ban = Ban.parseResponse(executeAPI())
}
