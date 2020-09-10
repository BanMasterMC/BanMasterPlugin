package pro.banmaster.bukkit.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import pro.banmaster.api.rest.ban.APIBanList
import pro.banmaster.api.rest.free.player.APIGetUser
import pro.banmaster.api.rest.mute.APIMuteList
import pro.banmaster.api.struct.BanType
import pro.banmaster.bukkit.BanMasterPlugin
import pro.banmaster.bukkit.BanMasterPlugin.Companion.color
import pro.banmaster.common.localization.Message
import util.CollectionList
import xyz.acrylicstyle.shared.BaseMojangAPI

class CommandWhois: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (BanMasterPlugin.invalidToken) {
            sender.sendMessage(Message.INVALID_TOKEN)
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(Message.INVALID_ARGUMENT)
            sender.sendMessage(Message.WHOIS_USAGE)
            return true
        }
        val uuid = try {
            BaseMojangAPI.getUniqueId(args[0])
        } catch (_: RuntimeException) {
            sender.sendMessage(Message.NO_PLAYER)
            return true
        }
        if (uuid == null) {
            sender.sendMessage(Message.NO_PLAYER)
            return true
        }
        APIGetUser(uuid).execute().then { user ->
            val bans = APIBanList(uuid, 1000, 0).execute()?.toCollectionList()
            val globalBans = bans?.filter { ban -> ban.type == BanType.GLOBAL }
            val localBans = (bans ?: CollectionList()).filter { ban -> ban.type == BanType.LOCAL }
            val mutes = APIMuteList(uuid, 1000, 0).execute()?.size ?: 0
            sender.sendMessage("${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.STRIKETHROUGH}------------------------------")
            if (BanMasterPlugin.debug) sender.sendMessage("${ChatColor.GRAY}[debug] ${color}User ID: ${ChatColor.GRAY}${user.id}")
            sender.sendMessage("${color}ID: ${ChatColor.GRAY}${user.name ?: args[0]}")
            sender.sendMessage("${color}UUID: ${ChatColor.GRAY}${user.uuid}")
            sender.sendMessage("${color}${Message.GLOBAL_BAN}: ${ChatColor.RED}${globalBans?.size ?: 0}")
            if (globalBans != null && globalBans.isNotEmpty()) {
                globalBans.forEach {
                    sender.sendMessage("${ChatColor.LIGHT_PURPLE} - ${ChatColor.GREEN}#${it.id}: ${ChatColor.WHITE}${it.reason} ${ChatColor.GRAY}(@${it.server.name})")
                }
            }
            val lb: Int = if(BanMasterPlugin.showLocalBans) localBans.size else localBans.filter { ban -> ban.server.id == (BanMasterPlugin.server?.id ?: 0) }.size
            sender.sendMessage("${color}${Message.LOCAL_BAN}: ${ChatColor.RED}${lb}${ChatColor.YELLOW}/${ChatColor.RED}${localBans.size}")
            if (localBans.isNotEmpty()) {
                localBans.forEach {
                    sender.sendMessage("${ChatColor.LIGHT_PURPLE} - ${ChatColor.GREEN}#${it.id}: ${ChatColor.WHITE}${it.reason} ${ChatColor.GRAY}(@${it.server.name})")
                }
            }
            sender.sendMessage("${color}${Message.MUTE_COUNT}: ${ChatColor.RED}${mutes}")
            sender.sendMessage("${color}${Message.COUNTRY}: ${ChatColor.LIGHT_PURPLE}${user.country ?: Message.UNKNOWN}")
            sender.sendMessage("${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.STRIKETHROUGH}------------------------------")
        }.queue()
        return true
    }
}
