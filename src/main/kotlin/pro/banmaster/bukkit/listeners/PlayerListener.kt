package pro.banmaster.bukkit.listeners

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import pro.banmaster.api.rest.free.player.APIGetUser
import pro.banmaster.api.rest.misc.APIJoin
import pro.banmaster.bukkit.BanMasterPlugin
import pro.banmaster.bukkit.BanMasterPlugin.Companion.saveIp
import pro.banmaster.common.util.checkPlayers
import util.CollectionList

class PlayerListener: Listener {
    @EventHandler
    fun onAsyncPlayerPreLogin(e: AsyncPlayerPreLoginEvent) { // todo: localization
        if (BanMasterPlugin.invalidToken) {
            val player = APIGetUser(e.uniqueId).execute().complete()
            if (player.strike_expire_at != null && player.strike_expire_at!! == 0L) {
                val list = CollectionList<String>()
                list.add("${ChatColor.RED}現在あなたはグローバルBAN(永久)されているため参加できません。")
                list.add("")
                list.add("${ChatColor.GRAY}ユーザーID: ${ChatColor.WHITE}${player.id}")
                list.add("")
                list.add("${ChatColor.GRAY}異議申し立て: ${ChatColor.AQUA}https://banmaster.pro")
                list.add("${ChatColor.GRAY}※8日以上経過すると異議申し立てができなくなります。")
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, list.join("\n"))
            }
        } else {
            var ip: String? = if (saveIp) e.address.hostAddress.replace("(.*):.*", "$1") else null
            if (checkPlayers()) ip = null
            val response = APIJoin(BanMasterPlugin.token!!, e.uniqueId, ip).execute().complete()
            if (response.isBanned) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, response.banData!!.getKickMessage())
            }
        }
    }
}
