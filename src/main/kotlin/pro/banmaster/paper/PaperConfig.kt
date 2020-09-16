package pro.banmaster.paper

object PaperConfig {
    private fun getBoolean(field: String): Boolean = Class.forName("com.destroystokyo.paper.PaperConfig").getField(field).getBoolean(null)

    fun isPresent(): Boolean {
        return try {
            Class.forName("com.destroystokyo.paper.PaperConfig")
            true
        } catch (_: ReflectiveOperationException) {
            false
        }
    }

    fun isProxyOnlineMode() = Class.forName("com.destroystokyo.paper.PaperConfig").getMethod("isProxyOnlineMode").invoke(null) as Boolean

    val bungeeOnlineMode: Boolean
        get() = getBoolean("bungeeOnlineMode")
}