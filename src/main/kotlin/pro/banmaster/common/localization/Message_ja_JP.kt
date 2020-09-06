package pro.banmaster.common.localization

import org.bukkit.ChatColor

class Message_ja_JP: Message("ja_JP") {
    override val languageCode = "ja_JP"
    override val invalidToken = ChatColor.RED.toString() + "APIキーが無効か、設定されていません。config.ymlで設定してサーバーを再起動してからもう一度試してください。"
    override val noArgument = ChatColor.RED.toString() + "引数がありません。"
    override val noPlayer = ChatColor.RED.toString() + "プレイヤーが見つかりません。"
    override val missingPermission = ChatColor.RED.toString() + "権限がありません。"
    override val banUsage = ChatColor.YELLOW.toString() + "/ban <プレイヤー> [<時間> <m/h/d>] <理由>"
    override val gbanUsage = ChatColor.YELLOW.toString() + "/gban <プレイヤー> <理由>"
    override val invalidTokenReadonly = "BanMasterは読み込み専用になります。"
    override val alreadyBannedPlayer = "${ChatColor.RED}%sはすでにBANされています。"
    override val bannedPlayer = "${ChatColor.GREEN}%sをBANしました: ${ChatColor.YELLOW}%s"
    override val gbannedPlayer = "${ChatColor.GREEN}%sをグローバルBANしました(ウェブサイトで証拠を提出する必要があります): ${ChatColor.YELLOW}%s"
    override val noAdvancedBanSupport = "${ChatColor.RED}このサーバーではAdvancedBanが有効になっていません。"
    override val advancedBanImportStart = "${ChatColor.GREEN}AdvancedBanからの移行を開始しました。しばらく時間がかかる場合があります。"
    override val advancedBanImportEnd = "${ChatColor.GREEN}AdvancedBanからの移行が完了しました。"
    override val advancedBanImportError = "${ChatColor.RED}AdvancedBanからの移行中にエラーが発生しました。"
}
