package pro.banmaster.api.struct

import org.bukkit.ChatColor
import org.json.JSONObject
import pro.banmaster.api.BanMasterAPI
import util.JSONAPI
import java.util.*

infix operator fun ChatColor.plus(s: String): String = this.toString() + s

fun preprocessResponse(response: JSONAPI.Response<JSONObject>) {
    if (response.responseCode != 200) throw RuntimeException("Response Code isn't OK: ${response.responseCode} (${response.rawResponse})")
    if (response.response.isEmpty) throw RuntimeException("Could not parse response: ${response.rawResponse} (Response Code: ${response.responseCode})")
}

private interface StaticStruct {
    fun parse(json: JSONObject): Struct

    fun parseResponse(response: JSONAPI.Response<JSONObject>): Struct
}

interface Struct {
    //fun update(): Promise<Void>

    //fun insert(): Promise<Void>
}

interface Warn: Struct {
    val id: Int
    val server: Server
    val target: User
    val punisher: User
    val reason: String
    val timestamp: Long

    companion object: StaticStruct {
        override fun parse(json: JSONObject): Warn {
            val id = if (json.has("WARNID")) json.getInt("WARNID") else json.getInt("ID")
            val server = Server.parse(json.getJSONObject("SERVER"))
            val target = User.parse(json.getJSONObject("TARGET"))
            val punisher = User.parse(json.getJSONObject("PUNISHER"))
            val reason = json.getString("REASON")
            val timestamp = json.getLong("TIMESTAMP")
            return BanMasterAPI.getInstance().createWarn(id, server, target, punisher, reason, timestamp)
        }

        override fun parseResponse(response: JSONAPI.Response<JSONObject>): Warn {
            preprocessResponse(response)
            return parse(response.response.getJSONObject("DATA"))
        }
    }
}

interface AdminEntry: Struct {
    val server: Server
    val user: User

    companion object: StaticStruct {
        override fun parse(json: JSONObject): AdminEntry {
            val server = Server.parse(json.getJSONObject("SERVER"))
            val user = User.parse(json.getJSONObject("USER"))
            return BanMasterAPI.getInstance().createAdminEntry(server, user)
        }

        override fun parseResponse(response: JSONAPI.Response<JSONObject>): AdminEntry {
            preprocessResponse(response)
            return parse(response.response.getJSONObject("DATA"))
        }
    }
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

    companion object: StaticStruct {
        override fun parse(json: JSONObject): User {
            val id = json.getInt("ID")
            val uuid = json.getString("UUID").toUUID()
            val name = json.getString("NAME", null)
            val strikeExpireAt = json.getLong("STRIKEEXPIRE")
            val admin = json.getBoolean("ADMIN")
            return BanMasterAPI.getInstance().createUser(id, uuid, name, null, null, 0L, null, 0L, strikeExpireAt, admin)
        }

        override fun parseResponse(response: JSONAPI.Response<JSONObject>): User {
            preprocessResponse(response)
            return parse(response.response.getJSONObject("DATA"))
        }
    }
}

interface Server: Struct {
    val id: Int
    val name: String
    val owner: User
    val rule: String?
    val address: String?
    val token: String?
    val private: Boolean
    val admin: Boolean
    val premium: Boolean
    val verified: Boolean
    val banned: Boolean
    val disabled: Boolean

    companion object: StaticStruct {
        override fun parse(json: JSONObject): Server {
            val id = json.getInt("ID")
            val ip = json.getString("IP")
            val owner = User.parse(json.getJSONObject("OWNER"))
            val admin = json.getBoolean("ADMIN", false)
            val private = json.getBoolean("PRIVATE", true)
            val premium = json.getBoolean("PREMIUM", false)
            val verified = json.getBoolean("VERIFIED", false)
            val banned = json.getBoolean("BANNED", false)
            val disabled = json.getBoolean("DISABLED", false)
            return BanMasterAPI.getInstance().createServer(id, ip, owner, null, null, null, private, admin, premium, verified, banned, disabled)
        }

        override fun parseResponse(response: JSONAPI.Response<JSONObject>): Server {
            preprocessResponse(response)
            return parse(response.response.getJSONObject("DATA"))
        }
    }
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
    val category: Int

    companion object: StaticStruct {
        override fun parse(json: JSONObject): Ban {
            val id = if (json.has("BANID")) json.getInt("BANID") else json.getInt("ID")
            val target = User.parse(json.getJSONObject("TARGET"))
            val punisher = User.parse(json.getJSONObject("PUNISHER"))
            val server = Server.parse(json.getJSONObject("SERVER"))
            val reason = json.getString("REASON")
            val expiresAt = if (json.has("EXPIRESAT")) json.getLong("EXPIRESAT") else 0L
            val timestamp = json.getLong("TIMESTAMP")
            val unbanned = json.getBoolean("UNBANNED", false)
            val unbanner = if (unbanned) User.parse(json.getJSONObject("UNBANNER")) else null
            val type = if (json.getBoolean("GLOBAL", false)) BanType.GLOBAL else BanType.LOCAL
            val verified = json.getBoolean("VERIFIED", false)
            val category = BanCategory.read(json.getJSONObject("CATEGORY"))
            return BanMasterAPI.getInstance().createBan(id, target, punisher, server, type, reason, timestamp, expiresAt, unbanner, unbanned, verified, category)
        }

        override fun parseResponse(response: JSONAPI.Response<JSONObject>): Ban {
            preprocessResponse(response)
            return parse(response.response.getJSONObject("DATA"))
        }
    }

    fun getKickMessage(): String
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
    val cancelPlayer: User?
    val type: MuteType

    companion object: StaticStruct {
        override fun parse(json: JSONObject): Mute {
            val id = if (json.has("MUTEID")) json.getInt("MUTEID") else json.getInt("ID")
            val target = User.parse(json.getJSONObject("TARGET"))
            val punisher = User.parse(json.getJSONObject("PUNISHER"))
            val server = Server.parse(json.getJSONObject("SERVER"))
            val reason = json.getString("REASON")
            val expiresAt = json.getLong("EXPIRESAT")
            val timestamp = json.getLong("TIMESTAMP")
            val cancelled = json.getBoolean("CANCELLED")
            val cancelPlayer = if (cancelled) User.parse(json.getJSONObject("CANCELLEXECUTER")) else null
            val type = if (json.getBoolean("GLOBAL", false)) MuteType.GLOBAL else MuteType.LOCAL
            return BanMasterAPI.getInstance().createMute(id, target, server, punisher, reason, timestamp, expiresAt, cancelled, cancelPlayer, type)
        }

        override fun parseResponse(response: JSONAPI.Response<JSONObject>): Struct {
            preprocessResponse(response)
            return parse(response.response.getJSONObject("DATA"))
        }
    }
}

fun JSONObject.getLongOf(key: String): Long? = if (this.has(key)) this.getJSONObject(key).getLong("Int64") else null

fun JSONObject.getString(key: String, default: String?): String? = if (has(key)) this.getString(key) else default

fun JSONObject.getBoolean(key: String, default: Boolean): Boolean = if (has(key)) this.getBoolean(key) else default

fun UUID.noHyphens() = this.toString().replace("-", "")

fun String.toUUID(): UUID = UUID.fromString(this.replace("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)".toRegex(), "$1-$2-$3-$4-$5"))

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

    fun read(json: JSONObject): Int {
        var category = 0
        if (json.getBoolean("GRIEFING", false)) category += GRIEFING
        if (json.getBoolean("XRAY", false)) category += X_RAY
        if (json.getBoolean("COMBATHACK", false)) category += HACK_COMBAT
        if (json.getBoolean("MOVEMENTHACK", false)) category += HACK_MOVEMENT
        if (json.getBoolean("OTHERHACK", false)) category += HACK_OTHER
        if (json.getBoolean("SPAMBOT", false)) category += SPAM_BOT
        if (json.getBoolean("OTHER", false)) category += OTHER
        return category
    }
}

object DataParser {
    val WARN = Warn.Companion::parse
    val BAN = Ban.Companion::parse
    val MUTE = Mute.Companion::parse
    val USER = User.Companion::parse
    val SERVER = Server.Companion::parse
    val ADMIN_ENTRY = AdminEntry.Companion::parse
    val BAN_CATEGORY = BanCategory::read
}
