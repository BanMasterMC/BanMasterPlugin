package pro.banmaster.bukkit.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import pro.banmaster.api.rest.misc.APIJoin
import pro.banmaster.bukkit.BanMasterPlugin

class PlayerListener: Listener {
    @EventHandler
    fun onAsyncPlayerPreLogin(e: AsyncPlayerPreLoginEvent) {
        if (!BanMasterPlugin.invalidToken)
            APIJoin(BanMasterPlugin.token!!, e.uniqueId, e.address.hostAddress.replace("(.*):.*", "$1")).execute().queue()
    }
}