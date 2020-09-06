package pro.banmaster.bukkit.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pro.banmaster.api.rest.ban.APILocalBan
import pro.banmaster.api.rest.ban.APITempBan
import pro.banmaster.api.rest.misc.APIAdminCheck
import pro.banmaster.bukkit.BanMasterPlugin
import pro.banmaster.bukkit.BanMasterPlugin.Companion.enforceAdminList
import pro.banmaster.bukkit.BanMasterPlugin.Companion.server
import pro.banmaster.bukkit.BanMasterPlugin.Companion.token
import pro.banmaster.common.localization.Message
import pro.banmaster.common.util.TypeUtil
import util.CollectionList
import util.ICollectionList
import xyz.acrylicstyle.shared.BaseMojangAPI

class CommandBan: CommandExecutor {
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
                sender.sendMessage(Message.BAN_USAGE)
                return@t
            }
            val player = args[0]
            val arg: CollectionList<String>
            run {
                val list = ICollectionList.asList(args) // doing this in block intentionally, to prevent access to "list" from outside of this
                if (args[1].matches("\\d+[mhd]".toRegex())) {
                    list.shift()
                    list.shift()
                    list.add(0, args[1].replace("\\d+([mhd])".toRegex(), "$1"))
                    list.add(0, args[1].replace("(\\d+)[mhd]".toRegex(), "$1"))
                    list.add(0, player)
                }
                arg = list.clone()
            }
            val uuid = BaseMojangAPI.getUniqueId(arg.shift()!!)
            if (uuid == null) {
                sender.sendMessage(Message.NO_PLAYER)
                return@t
            }
            var expiresAt: Long = -1
            if (arg.size >= 3) {
                if (TypeUtil.isInt(arg[0])) {
                    expiresAt = when (arg[1]) {
                        "d" -> System.currentTimeMillis() + arg[0].toLong() * DAY
                        "h" -> System.currentTimeMillis() + arg[0].toLong() * HOUR
                        "m" -> System.currentTimeMillis() + arg[0].toLong() * MINUTE
                        else -> -1
                    }
                    if (expiresAt != -1L) arg.shiftChain().shiftChain()
                }
            }
            val reason = arg.join(" ")
            val kick: String
            try {
                if (expiresAt == -1L) {
                    kick = APILocalBan(
                        token!!,
                        reason,
                        uuid,
                        if (sender is Player) sender.uniqueId else server!!.owner.uuid
                    ).execute().getKickMessage()
                } else {
                    kick = APITempBan(
                        token!!,
                        reason,
                        uuid,
                        if (sender is Player) sender.uniqueId else server!!.owner.uuid,
                        expiresAt
                    ).execute().getKickMessage()
                }
            } catch (e: RuntimeException) {
                if (BanMasterPlugin.debug) e.printStackTrace()
                sender.sendMessage(String.format(Message.ALREADY_BANNED_PLAYER, player))
                return@t
            }
            Bukkit.getPlayer(uuid)?.kickPlayer(kick)
            sender.sendMessage(String.format(Message.BANNED_PLAYER, player, reason))
        }.start()
        return true
    }

    companion object {
        const val DAY: Long = 86_400_000
        const val HOUR: Long = 3_600_000
        const val MINUTE: Long =  60_000
    }
}
