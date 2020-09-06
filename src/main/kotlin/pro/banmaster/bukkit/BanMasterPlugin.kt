package pro.banmaster.bukkit

import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import pro.banmaster.api.BanMasterAPI
import pro.banmaster.api.rest.misc.APIVerifyToken
import pro.banmaster.api.struct.Server
import pro.banmaster.bukkit.commands.CommandBan
import pro.banmaster.bukkit.config.ConfigProvider
import pro.banmaster.common.localization.Message
import java.util.logging.Logger

class BanMasterPlugin: BanMasterAPIImpl() {
    companion object {
        lateinit var conf: ConfigProvider
        var token: String? = ""
        lateinit var log: Logger
        var invalidToken = false
        var server: Server? = null
        var enforceAdminList = false
    }

    override fun onEnable() {
        log = logger
        conflicts("MCBans", "コマンドの競合")
        Bukkit.getServicesManager().register(BanMasterAPI::class.java, this, this, ServicePriority.Normal)
        Bukkit.getPluginCommand("ban").executor = CommandBan()
        reload()
    }

    private fun reload() {
        conf = ConfigProvider.getConfig("./plugins/BanMaster/config.yml")
        if (conf.getBoolean("enforceAdminList")) enforceAdminList = true
        token = conf.getString("token")
        if (token == null) {
            invalidToken = true
        } else {
            try {
                BanMasterPlugin.server = APIVerifyToken(token!!).execute()
            } catch (_: RuntimeException) {
                invalidToken = true
            }
        }
        if (invalidToken) {
            log.warning(Message.INVALID_TOKEN)
            log.warning(Message.INVALID_TOKEN_READONLY)
        }
    }

    private fun conflicts(plugin: String, reason: String) {
        if (Bukkit.getPluginManager().getPlugin(plugin) != null)
            throw RuntimeException("'$plugin'はこのプラグインとは非対応です。${plugin}を削除してサーバーを再起動してください。(理由: $reason)")
    }
}
