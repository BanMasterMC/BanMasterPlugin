package pro.banmaster.bukkit.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pro.banmaster.api.rest.misc.APIAdminCheck
import pro.banmaster.api.rest.warn.APIWarn
import pro.banmaster.bukkit.BanMasterPlugin
import pro.banmaster.common.localization.Message
import util.ICollectionList
import xyz.acrylicstyle.shared.BaseMojangAPI

class CommandWarn: CommandExecutor { // todo: WIP
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (BanMasterPlugin.invalidToken) {
            sender.sendMessage(Message.INVALID_TOKEN)
            return true
        }
        Thread t@ {
            if (BanMasterPlugin.enforceAdminList && sender is Player) {
                if (!APIAdminCheck(BanMasterPlugin.token!!, sender.uniqueId).execute().complete().data) {
                    sender.sendMessage(Message.MISSING_PERMISSION)
                    return@t
                }
            }
            if (args.size < 2) {
                sender.sendMessage(Message.INVALID_ARGUMENT)
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
            try {
                APIWarn(
                    BanMasterPlugin.token!!,
                    reason,
                    uuid,
                    if (sender is Player) sender.uniqueId else BanMasterPlugin.server!!.owner.uuid
                ).execute()
            } catch (e: RuntimeException) {
                e.printStackTrace()
                sender.sendMessage(String.format(Message.ERROR, e.javaClass.name))
                return@t
            }
            sender.sendMessage(String.format(Message.WARNED_PLAYER, player, reason))
        }.start()
        return true
    }
}