package pro.banmaster.common.util

import org.bukkit.Bukkit
import util.CollectionList

fun checkPlayers(): Boolean {
    val list = CollectionList(Bukkit.getOnlinePlayers()).map { p -> p.address.address.address }
    var value = false
    Bukkit.getOnlinePlayers().forEach {
        if (list.distribution(it.address.address.address) >= 0.8) {
            value = true
        }
    }
    return value
}
