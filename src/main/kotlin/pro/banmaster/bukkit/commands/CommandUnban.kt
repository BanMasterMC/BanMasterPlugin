package pro.banmaster.bukkit.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pro.banmaster.api.rest.ban.APIUnban
import pro.banmaster.api.rest.misc.APIAdminCheck
import pro.banmaster.bukkit.BanMasterPlugin
import pro.banmaster.bukkit.BanMasterPlugin.Companion.invalidToken
import pro.banmaster.bukkit.BanMasterPlugin.Companion.server
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import pro.banmaster.common.localization.Message
import xyz.acrylicstyle.shared.BaseMojangAPI

class CommandUnban: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (invalidToken) {
            sender.sendMessage(Message.INVALID_TOKEN)
            return true
        }
        Thread t@ {
            if (BanMasterPlugin.enforceAdminList && sender is Player) {
                if (!APIAdminCheck(token!!, sender.uniqueId).execute().complete().data) {
                    sender.sendMessage(Message.MISSING_PERMISSION)
                    return@t
                }
            }
            if (args.isEmpty()) {
                sender.sendMessage(Message.INVALID_ARGUMENT)
                sender.sendMessage(Message.UNBAN_USAGE)
                return@t
            }
            val uuid = BaseMojangAPI.getUniqueId(args[0])
            if (uuid == null) {
                sender.sendMessage(Message.NO_PLAYER)
                return@t
            }
            val player = if (sender is Player) sender.uniqueId else server!!.owner.uuid
            APIUnban(uuid, player).execute()
            sender.sendMessage(String.format(Message.UNBANNED_PLAYER, args[0]))
        }.start()
        return true
    }
}
