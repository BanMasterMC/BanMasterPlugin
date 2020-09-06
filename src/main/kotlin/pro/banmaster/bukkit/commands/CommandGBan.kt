package pro.banmaster.bukkit.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pro.banmaster.api.rest.ban.APIGlobalBan
import pro.banmaster.api.rest.misc.APIAdminCheck
import pro.banmaster.bukkit.BanMasterPlugin
import pro.banmaster.bukkit.BanMasterPlugin.Companion.enforceAdminList
import pro.banmaster.bukkit.BanMasterPlugin.Companion.server
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import pro.banmaster.common.localization.Message
import util.ICollectionList
import xyz.acrylicstyle.shared.BaseMojangAPI

class CommandGBan: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (server == null) {
            sender.sendMessage(Message.INVALID_TOKEN)
            return true
        }
        Thread t@ {
            if (enforceAdminList && sender is Player) {
                if (!APIAdminCheck(token!!, sender.uniqueId).execute().complete().data) {
                    sender.sendMessage(Message.MISSING_PERMISSION)
                    return@t
                }
            }
            if (args.size < 2) {
                sender.sendMessage(Message.NO_ARGUMENT)
                sender.sendMessage(Message.GBAN_USAGE)
                return@t
            }
            val player = args[0]
            val arg = ICollectionList.asList(args)
            val uuid = BaseMojangAPI.getUniqueId(arg.shift()!!)
            if (uuid == null) {
                sender.sendMessage(Message.NO_PLAYER)
                return@t
            }
            val reason = arg.join(" ")
            val kick: String
            try {
                kick = APIGlobalBan(
                    token!!,
                    reason,
                    uuid,
                    if (sender is Player) sender.uniqueId else server!!.owner.uuid
                ).execute().getKickMessage()
            } catch (e: RuntimeException) {
                if (BanMasterPlugin.debug) e.printStackTrace()
                sender.sendMessage(String.format(Message.ALREADY_BANNED_PLAYER, player))
                return@t
            }
            Bukkit.getPlayer(uuid)?.kickPlayer(kick)
            sender.sendMessage(String.format(Message.GBANNED_PLAYER, player, reason))
        }.start()
        return true
    }
}
