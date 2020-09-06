package pro.banmaster.bukkit

import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import pro.banmaster.api.BanMasterAPI

class BanMasterPlugin: BanMasterAPIImpl() {
    override fun onEnable() {
        conflicts("MCBans")
        Bukkit.getServicesManager().register(BanMasterAPI::class.java, this, this, ServicePriority.Normal)
    }

    private fun conflicts(plugin: String) {
        if (Bukkit.getPluginManager().getPlugin(plugin) != null)
            throw RuntimeException("'$plugin'はこのプラグインとは非対応です。${plugin}を削除してサーバーを再起動してください。")
    }
}
