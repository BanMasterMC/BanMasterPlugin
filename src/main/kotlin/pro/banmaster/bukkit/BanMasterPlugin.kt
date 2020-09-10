package pro.banmaster.bukkit

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.ServicePriority
import pro.banmaster.api.BanMasterAPI
import pro.banmaster.api.rest.misc.APIVerifyToken
import pro.banmaster.api.struct.Server
import pro.banmaster.bukkit.commands.*
import pro.banmaster.bukkit.config.ConfigProvider
import pro.banmaster.bukkit.listeners.PlayerListener
import pro.banmaster.common.localization.Message
import pro.banmaster.spigot.PaperConfig
import pro.banmaster.spigot.SpigotConfig
import xyz.acrylicstyle.tomeito_api.utils.Log

class BanMasterPlugin: BanMasterAPIImpl() {
    companion object {
        val log = Log.with("BanMaster")

        lateinit var conf: ConfigProvider
        var token: String? = ""
        var saveIp = false
        var showLocalBans = false
        var invalidToken = false
        var server: Server? = null
        var enforceAdminList = false
        var debug = java.lang.Boolean.getBoolean("pro.banmaster.debug")
        var color = ChatColor.WHITE
    }

    override fun onEnable() {
        reload()
        val onlineMode = if (PaperConfig.isPresent()) PaperConfig.isProxyOnlineMode() else {
            if (Bukkit.getOnlineMode()) {
                true
            } else {
                if (SpigotConfig.isPresent()) SpigotConfig.bungee else false
            }
        }
        if (!onlineMode) {
            log.severe(Message.OFFLINE_MODE)
            Bukkit.getPluginManager().disablePlugin(this)
            throw RuntimeException()
        }
        config.options().copyDefaults(true)
        conflicts("MCBans", "コマンドの競合") // todo: localization
        Bukkit.getServicesManager().register(BanMasterAPI::class.java, this, this, ServicePriority.Normal)
        Bukkit.getPluginManager().registerEvents(PlayerListener(), this)
        Bukkit.getPluginCommand("ban").executor = CommandBan()
        Bukkit.getPluginCommand("gban").executor = CommandGBan()
        Bukkit.getPluginCommand("unban").executor = CommandUnban()
        Bukkit.getPluginCommand("whois").executor = CommandWhois()
        Bukkit.getPluginCommand("warn").executor = CommandWarn()
        Bukkit.getPluginCommand("proof").executor = CommandProof()
    }

    private fun reload() {
        conf = ConfigProvider.getConfig("./plugins/BanMaster/config.yml")
        Message.register()
        Message.language = conf.getString("language", "ja_JP")
        log.info("Setting language: ${Message.LOCALE}")
        saveIp = conf.getBoolean("save-ip", false)
        showLocalBans = conf.getBoolean("showLocalBans", false)
        enforceAdminList = conf.getBoolean("enforceAdminList", false)
        color = ChatColor.valueOf(config.getString("white-to", "WHITE"))
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
            throw RuntimeException("'$plugin'はこのプラグインとは非対応です。${plugin}を削除してサーバーを再起動してください。(理由: $reason)") // todo: localization
    }
}
