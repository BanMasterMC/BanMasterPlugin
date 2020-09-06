package pro.banmaster.api.rest.free.mute

import org.json.JSONObject
import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.SimpleList
import pro.banmaster.api.struct.noHyphens
import java.util.UUID

class APIUserMuteList(uuid: UUID): APIRequest(
    "/free/mutelist/user",
    "POST",
    BodyBuilder().setJSON(JSONObject().put("uuid", uuid.noHyphens())).build(),
) {
    override fun execute(): SimpleList<Int> = SimpleList.parse(executeAPI()) { it as Int }
}
