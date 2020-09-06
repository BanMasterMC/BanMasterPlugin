package pro.banmaster.api.rest.mute

import pro.banmaster.api.rest.APIRequest

class APIServerMuteList: APIRequest(
        "/free/mutelist/user",
        "POST",
)
