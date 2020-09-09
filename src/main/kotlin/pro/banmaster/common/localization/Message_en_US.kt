package pro.banmaster.common.localization

import org.bukkit.ChatColor.*

class Message_en_US: Message("en_US") {
    override val locale = "English"
    override val invalidToken = "${RED}API key is invalid or not configured. Please set in config_en_US.yml, restart server, then try again."
    override val invalidArgument = "${RED}Invalid arguments."
    override val noPlayer = "${RED}Could not find player."
    override val missingPermission = "${RED}You don't have permission to do this."
    override val proofUsage = "${YELLOW}/proof <Player> <URL of the proof>"
    override val banUsage = "${YELLOW}/ban <Player> [<time> <m/h/d>] <Reason>"
    override val tbanUsage = "${YELLOW}/tban <Player> <Time> <m/h/d> <Reason>"
    override val gbanUsage = "${YELLOW}/gban <Player> <Reason>"
    override val unbanUsage = "${YELLOW}/unban <Player>"
    override val whoisUsage = "${YELLOW}/whois <Player>"
    override val invalidTokenReadonly = "BanMaster will be read-only access."
    override val alreadyBannedPlayer = "${RED}Player %s is already banned, or this ban doesn't meet requirements."
    override val bannedPlayer = "${GREEN}Banned %s: ${YELLOW}%s"
    override val gbannedPlayer = "${GREEN}Banned %s globally (You have to submit probes on website): ${YELLOW}%s"
    override val unbannedPlayer = "${GREEN}Unbanned %s."
    override val warnedPlayer = "${GREEN}Warned %s: ${RED}%s"
    override val error = "${RED}An internal error occurred while handling command. Please try again later. (%s)"
    override val timeNotSpecified = "${RED}Time isn't specified."
    override val offlineMode = "${RED}Server is in offline mode. You must set the online-mode to true or bungeecord (in spigot.yml) to true"
    override val proofSubmitted = "${GREEN}Submitted proof for %s. (${YELLOW}%s${GREEN})"
    override val invalidUrl = "${RED}Invalid URL."

    override val country = "Country"
    override val playerName = "Player Name"
    override val localBan = "Local Ban"
    override val globalBan = "Global Ban"
    override val muteCount = "Mute Count"

    override val noAdvancedBanSupport = "${RED}This server does not have AdvancedBan support."
    override val advancedBanImportStart = "${GREEN}Started the importing from AdvancedBan. It may take a while."
    override val advancedBanImportEnd = "${GREEN}Punishments(Bans) were successfully imported from AdvancedBan."
    override val advancedBanImportError = "${RED}An error occurred while importing from AdvancedBan."
}
