package pro.banmaster.api.struct

import java.util.*

interface Struct {
    //fun update(): Promise<Void>

    //fun insert(): Promise<Void>
}

interface Warn: Struct {
    val id: Long
    val server: Server
    val target: User
    val punisher: User
    val reason: String
    val timestamp: Long
}

interface AdminEntry: Struct {
    val server: Server
    val user: User
}

interface User: Struct {
    val id: Int
    val uuid: UUID
    val name: String?
    val ip: String?
    val discord_id: String?
    val discord_id_updated_date: Long
    val country: String?
    val country_updated_date: Long
    var strike_expire_at: Long?
    val admin: Boolean
}

interface Server: Struct {
    val id: Int
    val name: String
    val owner: User
    val rule: String
    val address: String
    val token: String?
    val private: Boolean
    val admin: Boolean
    val premium: Boolean
    val verified: Boolean
    val banned: Boolean
    val disabled: Boolean
}

interface Ban: Struct {
    val id: Int
    val player: User
    val punisher: User
    val server: Server
    val type: BanType
    val reason: String
    val timestamp: Long
    val expiresAt: Long
    val unbanner: User?
    val unbanned: Boolean
    val verified: Boolean
}

interface Mute: Struct {
    val id: Int
    val player: User
    val server: Server
    val executer: User
    val reason: String
    val timestamp: Long
    val expiresAt: Long?
    val cancelled: Boolean
    val cancel_player: User?
    val type: MuteType
}

interface BannedAltEntry: Struct {
    val player: User
    val ban: Ban
}

enum class MuteType {
    LOCAL, GLOBAL
}

enum class BanType {
    LOCAL, GLOBAL
}

object BanCategory {
    const val GRIEFING = 1 shl 0
    const val X_RAY = 1 shl 1
    const val HACK_COMBAT = 1 shl 2
    const val HACK_MOVEMENT = 1 shl 3
    const val HACK_OTHER = 1 shl 4
    const val SPAM_BOT = 1 shl 5
    const val OTHER = 1 shl 6
}
