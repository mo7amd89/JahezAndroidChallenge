package net.jahez.jahezchallenge

import android.content.res.Configuration
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import java.util.*

open class BaseAppCompatActivity : AppCompatActivity() {

    companion object {
        var dLocale: Locale? = null
    }

    init {
        updateConfiguration(this@BaseAppCompatActivity)
    }

    private fun updateConfiguration(wrapper: ContextThemeWrapper) {
        if (dLocale == Locale("")) // Do nothing if dLocale is null
            return
        Locale.setDefault(dLocale!!)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }


}