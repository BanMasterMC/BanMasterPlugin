package pro.banmaster.common.util

object TypeUtil {
    fun isInt(s: String): Boolean {
        return try {
            Integer.parseInt(s)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}