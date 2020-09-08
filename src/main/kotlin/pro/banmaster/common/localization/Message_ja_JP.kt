package pro.banmaster.common.localization

import org.bukkit.ChatColor.*

class Message_ja_JP: Message("ja_JP") {
    override val locale = "日本語"
    override val invalidToken = "${RED}APIキーが無効か、設定されていません。config.ymlで設定してサーバーを再起動してからもう一度試してください。"
    override val invalidArgument = "${RED}引数が無効です。"
    override val noPlayer = "${RED}プレイヤーが見つかりません。"
    override val missingPermission = "${RED}権限がありません。"
    override val banUsage = "${YELLOW}/ban <プレイヤー> [<時間> <m/h/d>] <理由>"
    override val gbanUsage = "${YELLOW}/gban <プレイヤー> <理由>"
    override val unbanUsage = "${YELLOW}/gban <プレイヤー>"
    override val whoisUsage = "${YELLOW}/whois <プレイヤー>"
    override val invalidTokenReadonly = "${RED}BanMasterは読み込み専用になります。"
    override val alreadyBannedPlayer = "${RED}%sはすでにBANされているか、BANできる条件を満たしていません。"
    override val bannedPlayer = "${GREEN}%sをBANしました: ${YELLOW}%s"
    override val gbannedPlayer = "${GREEN}%sをグローバルBANしました(ウェブサイトで証拠を提出する必要があります): ${YELLOW}%s"
    override val unbannedPlayer = "${GREEN}%sのBANを解除しました。"
    override val warnedPlayer = "${GREEN}%sを警告しました: ${RED}%s"
    override val error = "${RED}コマンドを実行中に内部エラーが発生しました。あとでもう一度やり直してください。 (%s)"
    override val offlineMode = "${RED}サーバーはオフラインモードです。online-modeをtrueにするか、bungeecord(spigot.yml内)をtrueにする必要があります。"

    override val country = "地域"
    override val playerName = "プレイヤー名"
    override val localBan = "ローカルBAN"
    override val globalBan = "グローバルBAN"
    override val muteCount = "ミュート回数"

    override val noAdvancedBanSupport = "${RED}このサーバーではAdvancedBanが有効になっていません。"
    override val advancedBanImportStart = "${GREEN}AdvancedBanからの移行を開始しました。しばらく時間がかかる場合があります。"
    override val advancedBanImportEnd = "${GREEN}AdvancedBanからの移行が完了しました。"
    override val advancedBanImportError = "${RED}AdvancedBanからの移行中にエラーが発生しました。"
}
