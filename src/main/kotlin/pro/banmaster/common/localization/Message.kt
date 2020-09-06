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

        val NO_ARGUMENT: String
            get() = mapping[language]!!.noArgument

        val NO_PLAYER: String
            get() = mapping[language]!!.noPlayer

        val MISSING_PERMISSION: String
            get() = mapping[language]!!.missingPermission

        val BAN_USAGE: String
            get() = mapping[language]!!.banUsage

        val GBAN_USAGE: String
            get() = mapping[language]!!.gbanUsage

        val LANGUAGE_CODE: String
            get() = mapping[language]!!.languageCode

        val INVALID_TOKEN_READONLY: String
            get() = mapping[language]!!.invalidTokenReadonly

        val ALREADY_BANNED_PLAYER: String
            get() = mapping[language]!!.alreadyBannedPlayer

        val BANNED_PLAYER: String
            get() = mapping[language]!!.bannedPlayer

        val GBANNED_PLAYER: String
            get() = mapping[language]!!.gbannedPlayer

        val NO_ADVANCED_BAN_SUPPORT: String
            get() = mapping[language]!!.noAdvancedBanSupport

        val ADVANCED_BAN_IMPORT_START: String
            get() = mapping[language]!!.advancedBanImportStart

        val ADVANCED_BAN_IMPORT_END: String
            get() = mapping[language]!!.advancedBanImportEnd

        val ADVANCED_BAN_IMPORT_ERROR: String
            get() = mapping[language]!!.advancedBanImportError
    }

    abstract val languageCode: String
    abstract val invalidToken: String
    abstract val noArgument: String
    abstract val noPlayer: String
    abstract val missingPermission: String
    abstract val banUsage: String
    abstract val gbanUsage: String
    abstract val invalidTokenReadonly: String
    abstract val alreadyBannedPlayer: String
    abstract val bannedPlayer: String
    abstract val gbannedPlayer: String

    abstract val noAdvancedBanSupport: String
    abstract val advancedBanImportStart: String
    abstract val advancedBanImportEnd: String
    abstract val advancedBanImportError: String
}
