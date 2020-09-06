package pro.banmaster.bukkit.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import pro.banmaster.api.rest.misc.APIJoin

class PlayerListener: Listener {
    @EventHandler
    fun onAsyncPlayerPreLogin(e: AsyncPlayerPreLoginEvent) {
        APIJoin(e.uniqueId, e.address.hostAddress.replace("(.*):.*", "$1")).execute().queue()
    }
}