package pro.banmaster.api.rest.free.mute

import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.rest.response.SimpleUUIDList

class APIServerMuteList: APIRequest(
    "/free/mutelist/user",
    "POST",
) {
    override fun execute(): SimpleUUIDList = SimpleUUIDList.parse(executeAPI())
}
