package pro.banmaster.bukkit.listeners

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import pro.banmaster.api.rest.free.player.APIGetUser
import pro.banmaster.api.rest.misc.APIJoin
import pro.banmaster.bukkit.BanMasterPlugin
import pro.banmaster.common.struct.timestampToDay
import util.CollectionList

class PlayerListener: Listener {
    @EventHandler
    fun onAsyncPlayerPreLogin(e: AsyncPlayerPreLoginEvent) {
        if (BanMasterPlugin.invalidToken) {
            val player = APIGetUser(e.uniqueId).execute().complete()
            if (player.strike_expire_at != null && player.strike_expire_at!! > 0L) {
                val list = CollectionList<String>()
                val currentTimestamp = System.currentTimeMillis()
                list.add("${ChatColor.RED}現在あなたは処罰が期限切れになっていないため参加できません。")
                list.add("")
                list.add("${ChatColor.GRAY}処罰解除まであと${ChatColor.WHITE}${(player.strike_expire_at!! - currentTimestamp).timestampToDay()}")
                list.add("")
                list.add("${ChatColor.GRAY}ユーザーID: ${ChatColor.WHITE}${player.id}")
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, list.join("\n"))
            }
        } else {
            val response = APIJoin(BanMasterPlugin.token!!, e.uniqueId, e.address.hostAddress.replace("(.*):.*", "$1")).execute().complete()
            if (response.isBanned) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, response.banData!!.getKickMessage())
            }
        }
    }
}
