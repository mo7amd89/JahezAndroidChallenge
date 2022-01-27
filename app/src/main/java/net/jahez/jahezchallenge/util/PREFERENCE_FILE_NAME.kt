package net.jahez.jahezchallenge.util

import android.content.Context

private const val PREFERENCE_FILE_NAME = "Jahez_Prefs"
private const val KEY_LANGUAGE = "lang"

class CacheHelperLang(var context: Context) {


    fun saveCurrentLanguage(language: Int) {
        val sharedPreferences =
            context.getSharedPreferences(
                PREFERENCE_FILE_NAME,
                Context.MODE_PRIVATE
            )
        val value = sharedPreferences.getInt(KEY_LANGUAGE, Constant.LANGUAGE_AUTO)
        if (value == language) return
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_LANGUAGE, language)
        editor.apply()
    }


    // @JvmStatic
    val currentLanguage: Int
        get() {
            val sharedPreferences = context.getSharedPreferences(
                PREFERENCE_FILE_NAME,
                Context.MODE_PRIVATE
            )
            return sharedPreferences.getInt(KEY_LANGUAGE, Constant.LANGUAGE_EN_US)
        }
}