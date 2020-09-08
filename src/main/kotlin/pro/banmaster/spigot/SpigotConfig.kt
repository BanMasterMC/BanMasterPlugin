package pro.banmaster.spigot

object SpigotConfig {
    private fun getBoolean(field: String): Boolean = Class.forName("org.spigotmc.SpigotConfig").getField(field).getBoolean(null)

    fun isPresent(): Boolean {
        return try {
            Class.forName("org.spigotmc.SpigotConfig")
            true
        } catch (_: ReflectiveOperationException) {
            false
        }
    }

    val bungee: Boolean
        get() = getBoolean("bungee")
}