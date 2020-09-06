package pro.banmaster.api.rest.misc

import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.Server

class APIVerifyToken(token: String): APIRequest(
    "/verifyToken?token=$token",
    "POST",
) {
    override fun execute(): Server = Server.parseResponse(executeAPI())
}
