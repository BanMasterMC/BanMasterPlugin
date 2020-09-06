package pro.banmaster.bukkit

import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import pro.banmaster.api.BanMasterAPI
import pro.banmaster.api.rest.misc.APIVerifyToken
import pro.banmaster.api.struct.Server
import pro.banmaster.bukkit.commands.CommandBan
import pro.banmaster.bukkit.commands.CommandGBan
import pro.banmaster.bukkit.config.ConfigProvider
import pro.banmaster.bukkit.listeners.PlayerListener
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
        var debug = java.lang.Boolean.getBoolean("pro.banmaster.debug")
    }

    override fun onEnable() {
        log = logger
        conflicts("MCBans", "コマンドの競合")
        Bukkit.getServicesManager().register(BanMasterAPI::class.java, this, this, ServicePriority.Normal)
        Bukkit.getPluginManager().registerEvents(PlayerListener(), this)
        Bukkit.getPluginCommand("ban").executor = CommandBan()
        Bukkit.getPluginCommand("gban").executor = CommandGBan()
        reload()
    }

    private fun reload() {
        conf = ConfigProvider.getConfig("./plugins/BanMaster/config.yml")
        Message.register()
        Message.language = conf.getString("language", "ja_JP")
        if (conf.getBoolean("enforceAdminList")) enforceAdminList = true
        token = conf.getString("token")
        if (token == null) {
            invalidToken = true
        } else {
            try {
                BanMasterPlugin.server = APIVerifyToken(token!!).execute()
            } catch (e: RuntimeException) {
                e.printStackTrace()
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
