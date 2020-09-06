package pro.banmaster.bukkit.config

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import util.SneakyThrow
import java.io.File
import java.io.IOException
import java.util.HashMap

class ConfigProvider @JvmOverloads constructor(path: String, disableConstructor: Boolean = false) : YamlConfiguration() {
    private val file: File
    private val path: String

    constructor(file: File) : this(file.absolutePath) {}

    fun getConfigSectionValue(path: String, deep: Boolean): Map<String, Any>? {
        val o = this[path, HashMap<String, Any>()]
        return getConfigSectionValue(o, deep)
    }

    fun reload() {
        try {
            if (!file.exists()) file.createNewFile()
            this.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun save() {
        try {
            this.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setThenSave(path: String, value: Any?) {
        this[path] = value
        this.save()
    }

    override fun load(file: File) {
        try {
            super.load(file)
        } catch (e: IOException) {
            System.err.println("An error occurred while loading a config file: " + file.absolutePath)
            SneakyThrow.sneaky<Any>(e)
        } catch (e: InvalidConfigurationException) {
            System.err.println("An error occurred while loading a config file: " + file.absolutePath)
            SneakyThrow.sneaky<Any>(e)
        } catch (e: RuntimeException) {
            System.err.println("An error occurred while loading a config file: " + file.absolutePath)
            SneakyThrow.sneaky<Any>(e)
        }
    }

    companion object {
        /**
         * @param path relative or absolute path from the spigot.jar
         * @return ConfigProvider
         */
        private fun initWithoutException(path: String): ConfigProvider? {
            return try {
                ConfigProvider(path)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        /**
         * @param file An configuration file
         * @return ConfigProvider
         */
        private fun initWithoutException(file: File): ConfigProvider? {
            return try {
                ConfigProvider(file)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun getConfig(plugin: Plugin, file: String?): ConfigProvider {
            return getConfig(File(plugin.dataFolder, file ?: "plugin.yml"))
        }

        fun getConfig(file: File): ConfigProvider {
            return initWithoutException(file) ?: throw NullPointerException()
        }

        fun getConfig(path: String): ConfigProvider {
            return initWithoutException(path) ?: throw NullPointerException()
        }

        fun getConfigSectionValue(o: Any?, deep: Boolean): Map<String, Any>? {
            if (o == null) return null
            if (o is ConfigurationSection) {
                return o.getValues(deep)
            } else if (o is Map<*, *>) {
                @Suppress("UNCHECKED_CAST")
                return o as Map<String, Any>?
            }
            return null
        }

        fun setThenSave(path: String?, value: Any?, file: File) {
            try {
                val config = YamlConfiguration()
                if (!file.exists()) config.save(file)
                config.load(file)
                config[path] = value
                config.save(file)
            } catch (e: IOException) {
                System.err.println("An error occurred while saving a config file: " + file.absolutePath)
                SneakyThrow.sneaky<Any>(e)
            } catch (e: InvalidConfigurationException) {
                System.err.println("An error occurred while saving a config file: " + file.absolutePath)
                SneakyThrow.sneaky<Any>(e)
            }
        }

        fun getBoolean(path: String?, def: Boolean?, pluginName: String): Boolean {
            return getBoolean(path, def, File("./plugins/$pluginName/config.yml"))
        }

        fun getBoolean(path: String?, def: Boolean?, file: File): Boolean {
            return getConfig(file).getBoolean(path, def!!)
        }

        fun getString(path: String?, def: String?, pluginName: String): String {
            return getString(path, def, File("./plugins/$pluginName/config.yml"))
        }

        fun getString(path: String?, def: String?, file: File): String {
            return getConfig(file).getString(path, def)
        }

        fun setThenSave(path: String?, value: Any?, pluginName: String) {
            setThenSave(path, value, File("./plugins/$pluginName/config.yml"))
        }
    }

    init {
        if (disableConstructor) throw UnsupportedOperationException()
        this.path = path
        file = File(this.path)
        if (!file.exists()) { // for avoid some dangerous situation
            file.mkdirs() // creates directory(ies) including file name
            file.delete() // deletes file but not parent directory
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        this.load(file)
    }
}
