package pro.banmaster.common.localization

abstract class Message(language: String) {
    init {
        @Suppress("LeakingThis")
        mapping[language] = this
    }

    companion object {
        private val mapping = HashMap<String, Message>()

        fun register() {
            Message_en_US()
            Message_ja_JP()
        }

        var language: String = "ja_JP"
            set(value) {
                if (!mapping.containsKey(value)) throw RuntimeException("Missing language mapping: $value")
                field = value
            }

        val INVALID_TOKEN: String
            get() = mapping[language]!!.invalidToken

        val INVALID_ARGUMENT: String
            get() = mapping[language]!!.invalidArgument

        val NO_PLAYER: String
            get() = mapping[language]!!.noPlayer

        val MISSING_PERMISSION: String
            get() = mapping[language]!!.missingPermission

        val PROOF_USAGE: String
            get() = mapping[language]!!.proofUsage

        val BAN_USAGE: String
            get() = mapping[language]!!.banUsage

        val TBAN_USAGE: String
            get() = mapping[language]!!.tbanUsage

        val GBAN_USAGE: String
            get() = mapping[language]!!.gbanUsage

        val UNBAN_USAGE: String
            get() = mapping[language]!!.unbanUsage

        val WHOIS_USAGE: String
            get() = mapping[language]!!.whoisUsage

        val LOCALE: String
            get() = mapping[language]!!.locale

        val INVALID_TOKEN_READONLY: String
            get() = mapping[language]!!.invalidTokenReadonly

        val ALREADY_BANNED_PLAYER: String
            get() = mapping[language]!!.alreadyBannedPlayer

        val BANNED_PLAYER: String
            get() = mapping[language]!!.bannedPlayer

        val GBANNED_PLAYER: String
            get() = mapping[language]!!.gbannedPlayer

        val UNBANNED_PLAYER: String
            get() = mapping[language]!!.unbannedPlayer

        val WARNED_PLAYER: String
            get() = mapping[language]!!.warnedPlayer

        val ERROR: String
            get() = mapping[language]!!.error

        val TIME_NOT_SPECIFIED: String
            get() = mapping[language]!!.timeNotSpecified

        val OFFLINE_MODE: String
            get() = mapping[language]!!.offlineMode

        val PROOF_SUBMITTED: String
            get() = mapping[language]!!.proofSubmitted

        val INVALID_URL: String
            get() = mapping[language]!!.invalidUrl


        val UNKNOWN: String
            get() = mapping[language]!!.unknown

        val COUNTRY: String
            get() = mapping[language]!!.country

        val PLAYER_NAME: String
            get() = mapping[language]!!.playerName

        val LOCAL_BAN: String
            get() = mapping[language]!!.localBan

        val GLOBAL_BAN: String
            get() = mapping[language]!!.globalBan

        val MUTE_COUNT: String
            get() = mapping[language]!!.muteCount


        val NO_ADVANCED_BAN_SUPPORT: String
            get() = mapping[language]!!.noAdvancedBanSupport

        val ADVANCED_BAN_IMPORT_START: String
            get() = mapping[language]!!.advancedBanImportStart

        val ADVANCED_BAN_IMPORT_END: String
            get() = mapping[language]!!.advancedBanImportEnd

        val ADVANCED_BAN_IMPORT_ERROR: String
            get() = mapping[language]!!.advancedBanImportError
    }

    abstract val locale: String
    abstract val invalidToken: String
    abstract val invalidArgument: String
    abstract val noPlayer: String
    abstract val missingPermission: String
    abstract val proofUsage: String
    abstract val banUsage: String
    abstract val tbanUsage: String
    abstract val gbanUsage: String
    abstract val unbanUsage: String
    abstract val whoisUsage: String
    abstract val invalidTokenReadonly: String
    abstract val alreadyBannedPlayer: String
    abstract val bannedPlayer: String
    abstract val gbannedPlayer: String
    abstract val unbannedPlayer: String
    abstract val warnedPlayer: String
    abstract val error: String
    abstract val timeNotSpecified: String
    abstract val offlineMode: String
    abstract val proofSubmitted: String
    abstract val invalidUrl: String

    abstract val unknown: String
    abstract val country: String
    abstract val playerName: String
    abstract val localBan: String
    abstract val globalBan: String
    abstract val muteCount: String

    abstract val noAdvancedBanSupport: String
    abstract val advancedBanImportStart: String
    abstract val advancedBanImportEnd: String
    abstract val advancedBanImportError: String
}
