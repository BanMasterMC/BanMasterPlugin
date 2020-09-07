package pro.banmaster.common.localization

import org.bukkit.ChatColor

class Message_en_US: Message("en_US") {
    override val locale = "English"
    override val invalidToken = ChatColor.RED.toString() + "API key is invalid or not configured. Please set in config.yml, restart server, then try again."
    override val noArgument = ChatColor.RED.toString() + "Invalid arguments."
    override val noPlayer = ChatColor.RED.toString() + "Could not find player."
    override val missingPermission = ChatColor.RED.toString() + "You don't have permission to do this."
    override val banUsage = ChatColor.YELLOW.toString() + "/ban <Player> [<time> <m/h/d>] <Reason>"
    override val gbanUsage = ChatColor.YELLOW.toString() + "/gban <Player> <Reason>"
    override val unbanUsage = ChatColor.YELLOW.toString() + "/unban <Player>"
    override val invalidTokenReadonly = "BanMaster will be read-only access."
    override val alreadyBannedPlayer = "${ChatColor.RED}Player %s is already banned."
    override val bannedPlayer = "${ChatColor.GREEN}Banned %s: ${ChatColor.YELLOW}%s"
    override val gbannedPlayer = "${ChatColor.GREEN}Banned %s globally (You have to submit probes on website): ${ChatColor.YELLOW}%s"
    override val unbannedPlayer = "${ChatColor.GREEN}Unbanned %s."

    override val noAdvancedBanSupport = "${ChatColor.RED}This server does not have AdvancedBan support."
    override val advancedBanImportStart = "${ChatColor.GREEN}Started the importing from AdvancedBan. It may take a while."
    override val advancedBanImportEnd = "${ChatColor.GREEN}Punishments(Bans) were successfully imported from AdvancedBan."
    override val advancedBanImportError = "${ChatColor.RED}An error occurred while importing from AdvancedBan."
}
