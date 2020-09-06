package pro.banmaster.api.rest.free.player

import pro.banmaster.api.rest.APIRequest
import pro.banmaster.api.struct.User
import pro.banmaster.api.struct.noHyphens
import util.promise.Promise
import java.util.UUID

class APIGetUser(uuid: UUID): APIRequest(
    "/free/user?player=${uuid.noHyphens()}",
    "GET",
) {
    override fun execute(): Promise<User> = Promise.async { User.parse(executeAPI().response) }
}
