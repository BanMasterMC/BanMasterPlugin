package pro.banmaster.api

import org.bukkit.Bukkit
import pro.banmaster.api.struct.*
import java.util.*

interface BanMasterAPI {
    companion object {
        fun getAPI(): BanMasterAPI? = Bukkit.getServicesManager().getRegistration(BanMasterAPI::class.java)?.provider
    }

    fun createWarn(id: Long, server: Server, target: User, punisher: User, reason: String, timestamp: Long): Warn

    fun createAdminEntry(server: Server, user: User): AdminEntry

    fun createBannedAltEntry(player: User, ban: Ban): BannedAltEntry

    fun createUser(id: Int,
                   uuid: UUID,
                   name: String?,
                   ip: String?,
                   discord_id: String?,
                   discord_id_updated_date: Long,
                   country: String?,
                   country_updated_date: Long,
                   strike_expire_at: Long?,
                   admin: Boolean): User

    fun createServer(id: Int,
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
                     disabled: Boolean): Server

    fun createBan(id: Int,
                  player: User,
                  punisher: User,
                  server: Server,
                  type: BanType,
                  reason: String,
                  timestamp: Long,
                  expiresAt: Long,
                  unbanner: User?,
                  unbanned: Boolean,
                  verified: Boolean): Ban

    fun createMute(id: Int,
                   player: User,
                   server: Server,
                   executer: User,
                   reason: String,
                   timestamp: Long,
                   expiresAt: Long?,
                   cancelled: Boolean,
                   cancel_player: User?,
                   type: MuteType): Mute
}
