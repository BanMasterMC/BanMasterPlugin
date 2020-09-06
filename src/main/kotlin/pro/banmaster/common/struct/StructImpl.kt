package pro.banmaster.common.struct

import org.bukkit.ChatColor
import pro.banmaster.api.struct.*
import pro.banmaster.bukkit.commands.CommandBan
import util.CollectionList
import java.util.*
import kotlin.math.exp

/**
 * Creates Warn object with specified information.
 * @param id the Warn ID
 * @param server the server
 * @param reason the reason of this warn
 */
class WarnImpl(
    override val id: Int,
    override val server: Server,
    override val target: User,
    override val punisher: User,
    override val reason: String,
    override val timestamp: Long,
): Struct, Warn {
    companion object {
        /**
         * Creates Warn object with server ID. A target server must exist.
         * @param id the warn ID
         * @param server_id the server ID
         * @param reason the reason of this warn
         * @return Newly created warn object
         */
        //fun createByServerId(id: Long, server_id: Int, target: User, punisher: User, reason: String, timestamp: Long): Promise<Warn> = Server.getById(server_id).then { Warn(id, it!!, target, punisher, reason, timestamp) }

        /**
         * Get Warn object by Warn ID.
         * @param id the warn ID
         * @return Warn object if found, otherwise null
         */
        //fun getById(id: Long): Promise<Warn?> = SqlUtils.getWarnById(id)
    }

    //override fun update() = SqlUtils.updateWarn(this, false)

    //override fun insert() = SqlUtils.updateWarn(this, true)
}

/**
 * Represents a single admin entry on the server.
 *
 * Creates Admin entry by specified information.
 * @param server the server where the player is an admin at
 * @param user the player
 */
class AdminEntryImpl(
    override val server: Server,
    override val user: User
): AdminEntry {
    companion object {
        /**
         * Creates Admin entry by specified server and uuid of the player.
         * After creating the new object, you need to call AdminEntry#upsert to apply changes.
         * @param server the server
         * @param uuid the uuid of the player
         * @return the newly created Admin entry object
         */
        //fun createAdminEntry(server: Server, uuid: UUID): Promise<AdminEntry> = User.getByUUID(uuid).then { AdminEntry(server, it!!) }

        /**
         * Get admins of the server.
         * @param server the server
         * @return the list of the admin.
         */
        //fun getAdmins(server: Server): Promise<CollectionList<AdminEntry>> = SqlUtils.getAdmins(server)

        /**
         * Get servers that the user is an admin at.
         * @param user the user
         * @return the list of the admin.
         */
        //fun getAdmins(user: User): Promise<CollectionList<AdminEntry>> = SqlUtils.getAdmins(user)
    }

    /*
    fun upsert(): Promise<Void> = ConnectionHolder.admins.upsert(
        UpsertOptions.Builder().addWhere("player", user.id).addWhere("server_id", server.id)
            .addValue("server_id", server.id).addValue("player", user.id).build()
    ).then { null }

    fun remove(): Promise<Void> = ConnectionHolder.admins.delete(
        FindOptions.Builder().addWhere("server_id", server.id).addWhere("player", user.id).build()
    ).then { null }
     */
}

/**
 * Creates User object with specified information.
 * @param id the user ID
 * @param uuid the UUID of this player
 * @param name the cached name of this player
 * @param ip the ip address of this player
 * @param discord_id their Discord ID
 * @param discord_id_updated_date Discord ID updated date, 0 indicates "never"
 * @param country the country detected by the server
 * @param country_updated_date Country updated date, 0 indicates "never"
 * @param strike_expire_at the date when the last strike expires (null = 0, date without 0 = 1, 0 = 2, indicates never expires)
 */
class UserImpl(
    override val id: Int,
    override val uuid: UUID,
    override val name: String?,
    override val ip: String?,
    override val discord_id: String?,
    override val discord_id_updated_date: Long,
    override val country: String?,
    override val country_updated_date: Long,
    override var strike_expire_at: Long?,
    override val admin: Boolean,
): Struct, User {
    companion object {
        /**
         * Finds user by their ID.
         * @param id the user ID
         * @return the user object, if any
         */
        //fun getById(id: Int): Promise<User?> = SqlUtils.getUserById(id)

        /**
         * Finds user by their UUID.
         * @param uuid the uuid of the player
         * @return the user object, if any
         */
        //fun getByUUID(uuid: UUID): Promise<User?> = SqlUtils.getUserByUUID(uuid)
    }

    //override fun update() = SqlUtils.updateUser(this, false)

    //override fun insert() = SqlUtils.updateUser(this, true)
}

/**
 * Creates server object with specified information.
 * @param id the server ID
 * @param name the server name
 * @param owner the owner of the server
 * @param rule the URL of their rule page
 * @param address the public server address of their server
 * @param token the api token associated with this server
 * @param private whether if this server wants to hide their server address on the website
 * @param premium whether if this server is premium
 * @param verified whether if this server is verified (api token was granted)
 * @param banned whether if this server is banned or not (banned server cannot do anything)
 */
class ServerImpl(
    override val id: Int,
    override val name: String,
    override val owner: User,
    override val rule: String?,
    override val address: String?,
    override val token: String?,
    override val private: Boolean,
    override val admin: Boolean,
    override val premium: Boolean,
    override val verified: Boolean,
    override val banned: Boolean,
    override val disabled: Boolean,
): Struct, Server {
    companion object {
        /**
         * Finds server by ID.
         * @param id the server ID
         * @return the server if any, null if not found
         */
        //fun getById(id: Int): Promise<Server?> = SqlUtils.getServerById(id)
    }

    //override fun update() = SqlUtils.updateServer(this, false)

    //override fun insert() = SqlUtils.updateServer(this, true)
}

/**
 * Creates ban object with specified information.
 * @param id the Ban ID
 * @param player the player who was banned by the player
 * @param punisher the player who banned the player
 * @param server the server where target was banned
 * @param type whether this ban is local or global
 * @param reason the reason of this ban
 * @param timestamp the timestamp of this ban
 * @param expiresAt the timestamp when this ban expires (-1 indicates never)
 * @param unbanner the player who unbanned this player
 * @param unbanned whether this ban was cancelled
 * @paraM verified whether this ban was verified by the website admin
 */
class BanImpl(
    override val id: Int,
    override val player: User,
    override val punisher: User,
    override val server: Server,
    override val type: BanType,
    override val reason: String,
    override val timestamp: Long,
    override val expiresAt: Long,
    override val unbanner: User?,
    override val unbanned: Boolean,
    override val verified: Boolean,
): Struct, Ban {
    companion object {
        /**
         * Finds Ban by ID.
         * @param id the Ban ID
         * @return ban object if any, null if not found
         */
        //fun getById(id: Int): Promise<Ban?> = SqlUtils.getBanById(id)
    }

    override fun getKickMessage(): String {
        val list = CollectionList<String>()
        val currentTimestamp = System.currentTimeMillis()
        if (player.strike_expire_at != null && player.strike_expire_at!! > 0L) {
            list.add("${ChatColor.RED}現在あなたは処罰が期限切れになっていないため参加できません。")
            list.add("${ChatColor.GRAY}理由: ${ChatColor.WHITE}${reason}")
            list.add("")
            list.add("${ChatColor.GRAY}処罰解除まであと${ChatColor.WHITE}${(player.strike_expire_at!! - currentTimestamp).timestampToDay()}")
            list.add("")
            list.add("${ChatColor.GRAY}ユーザーID: ${ChatColor.WHITE}${player.id} ${ChatColor.GRAY}| 処罰ID: ${ChatColor.WHITE}${id}")
            return list.join("\n")
        }
        val perm = expiresAt <= 0
        list.add("${ChatColor.RED}あなたはサーバーから${if (perm) "永久的に" else "一時的に"}BANされています。")
        list.add("${ChatColor.GRAY}理由: ${ChatColor.WHITE}${reason}")
        if (!perm) list.add("")
        if (!perm) list.add("${ChatColor.GRAY}BAN解除まであと${ChatColor.WHITE}${(expiresAt - currentTimestamp).timestampToDay()}")
        list.add("")
        list.add("${ChatColor.GRAY}ユーザーID: ${ChatColor.WHITE}${player.id} ${ChatColor.GRAY}| 処罰ID: ${ChatColor.WHITE}${id}")
        return list.join("\n")
    }

    //override fun update() = SqlUtils.updateBan(this, false)

    //override fun insert() = SqlUtils.updateBan(this, true)
}

/**
 * Creates mute object with specified information.
 * @param id the mute ID
 * @param player the player who was muted by the player
 * @param server the server where user was muted
 * @param executer the player who muted the player
 * @param reason the reason of this mute
 * @param timestamp the timestamp of this mute
 * @param expiresAt the timestamp when this mute expires (-1 indicates never)
 * @param cancelled whether this mute was cancelled
 * @param type whether this mute is local or global (if global, the player will be unable to speak on all servers)
 */
class MuteImpl(
    override val id: Int,
    override val player: User,
    override val server: Server,
    override val executer: User,
    override val reason: String,
    override val timestamp: Long,
    override val expiresAt: Long?,
    override val cancelled: Boolean,
    override val cancelPlayer: User?,
    override val type: MuteType
): Struct, Mute {
    companion object {
        /**
         * Finds mute by ID.
         * @param id the mute ID
         * @return mute object if any, null if not found
         */
        //fun getById(id: Int): Promise<Mute?> = SqlUtils.getMuteById(id)
    }

    //override fun update() = SqlUtils.updateMute(this, false)

    //override fun insert() = SqlUtils.updateMute(this, true)
}

class BannedAltEntryImpl(
    override val player: User,
    override val ban: Ban,
): BannedAltEntry

fun Long.timestampToDay(): String {
    val d: Long = this / CommandBan.DAY
    val h: Long = (this - d * CommandBan.DAY) / CommandBan.HOUR
    val m: Long = (this - (d * CommandBan.DAY + h * CommandBan.HOUR)) / CommandBan.MINUTE
    return (if (d < 1) "" else d.toString() + "d") + (if (d < 1 && h < 1) "" else h.toString() + "h") + m + "m"
}
