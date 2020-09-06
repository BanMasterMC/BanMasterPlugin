package pro.banmaster.bukkit

import org.bukkit.plugin.java.JavaPlugin
import pro.banmaster.api.BanMasterAPI
import pro.banmaster.api.struct.*
import pro.banmaster.common.struct.*
import java.util.*

abstract class BanMasterAPIImpl: JavaPlugin(), BanMasterAPI {
    override fun createWarn(
        id: Long,
        server: Server,
        target: User,
        punisher: User,
        reason: String,
        timestamp: Long
    ): Warn = WarnImpl(id, server, target, punisher, reason, timestamp)

    override fun createAdminEntry(server: Server, user: User): AdminEntry = AdminEntryImpl(server, user)

    override fun createBannedAltEntry(player: User, ban: Ban): BannedAltEntry = BannedAltEntryImpl(player, ban)

    override fun createUser(
        id: Int,
        uuid: UUID,
        name: String?,
        ip: String?,
        discord_id: String?,
        discord_id_updated_date: Long,
        country: String?,
        country_updated_date: Long,
        strike_expire_at: Long?,
        admin: Boolean
    ): User = UserImpl(id, uuid, name, ip, discord_id, discord_id_updated_date, country, country_updated_date, strike_expire_at, admin)

    override fun createServer(
        id: Int,
        name: String,
        owner: User,
        rule: String,
        address: String,
        token: String?,
        private: Boolean,
        admin: Boolean,
        premium: Boolean,
        verified: Boolean,
        banned: Boolean,
        disabled: Boolean
    ): Server = ServerImpl(id, name, owner, rule, address, token, private, admin, premium, verified, banned, disabled)

    override fun createBan(
        id: Int,
        player: User,
        punisher: User,
        server: Server,
        type: BanType,
        reason: String,
        timestamp: Long,
        expiresAt: Long,
        unbanner: User?,
        unbanned: Boolean,
        verified: Boolean
    ): Ban = BanImpl(id, player, punisher, server, type, reason, timestamp, expiresAt, unbanner, unbanned, verified)

    override fun createMute(
        id: Int,
        player: User,
        server: Server,
        executer: User,
        reason: String,
        timestamp: Long,
        expiresAt: Long?,
        cancelled: Boolean,
        cancel_player: User?,
        type: MuteType
    ): Mute = MuteImpl(id, player, server, executer, reason, timestamp, expiresAt, cancelled, cancel_player, type)
}