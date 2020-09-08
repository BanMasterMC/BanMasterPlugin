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
import pro.banmaster.common.localization.Message
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
        val uuid = BaseMojangAPI.getUniqueId(args[0])
        if (uuid == null) {
            sender.sendMessage(Message.NO_PLAYER)
            return true
        }
        APIGetUser(uuid).execute().then { user ->
            val bans = APIBanList(uuid, 1000, 0).execute().toCollectionList()
            val globalBans = bans.filter { ban -> ban.type == BanType.GLOBAL }
            val localBans = bans.filter { ban -> ban.type == BanType.LOCAL }
            val mutes = APIMuteList(uuid, 1000, 0).execute()
            sender.sendMessage(String.format(Message.WHOIS_RESULT, user.name ?: args[0], globalBans.size, localBans.size, mutes.size))
            sender.sendMessage("${ChatColor.YELLOW}ID: ${user.id}")
            sender.sendMessage("${ChatColor.YELLOW}${Message.PLAYER_NAME}: ${user.name ?: args[0]}")
            sender.sendMessage("${ChatColor.YELLOW}UUID: ${user.uuid}")
            sender.sendMessage("${ChatColor.YELLOW}${Message.COUNTRY}: ${user.country ?: "?"}")
            sender.sendMessage("${ChatColor.RED} = ${Message.GLOBAL_BAN} =")
            globalBans.forEach {
                sender.sendMessage("${ChatColor.AQUA}#${it.id}: ${ChatColor.WHITE}${it.reason} ${ChatColor.GRAY}(@${it.server.name})")
            }
            sender.sendMessage("${ChatColor.RED} = ${Message.LOCAL_BAN} =")
            localBans.forEach {
                sender.sendMessage("${ChatColor.AQUA}#${it.id}: ${ChatColor.WHITE}${it.reason} ${ChatColor.GRAY}(@${it.server.name})")
            }
        }.queue()
        return true
    }
}