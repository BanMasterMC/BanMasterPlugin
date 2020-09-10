package pro.banmaster.bukkit.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import pro.banmaster.api.rest.ban.APISendProof
import pro.banmaster.common.localization.Message
import util.ICollectionList
import xyz.acrylicstyle.shared.BaseMojangAPI

class CommandProof: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) {
            sender.sendMessage(Message.PROOF_USAGE)
            return true
        }
        val url = ICollectionList.asList(args).shiftChain().join(" ")
        if (!url.startsWith("http")) {
            sender.sendMessage(Message.INVALID_URL)
            return true
        }
        BaseMojangAPI.getUniqueIdAsync(args[0]).then { uuid ->
            try {
                APISendProof(uuid, url).execute()
            } catch (_: RuntimeException) {}
            sender.sendMessage(String.format(Message.PROOF_SUBMITTED, args[0], url))
        }.catch_<Throwable, Any> { it.printStackTrace() }.queue()
        return true
    }
}
