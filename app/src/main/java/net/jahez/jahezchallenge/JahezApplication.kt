package net.jahez.jahezchallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.jahez.jahezchallenge.util.CacheHelperLang
import java.util.*

@HiltAndroidApp
class JahezApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        val language: String = if (CacheHelperLang(applicationContext).currentLanguage == 1) "ar" else "en"

        BaseAppCompatActivity.dLocale = Locale(language)
    }
}