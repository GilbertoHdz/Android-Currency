package com.gilbertohdz.currency.di

import android.content.Context
import androidx.core.content.edit
import com.gilbertohdz.currency.lib.utils.prefs.ICurrencyPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CurrencyPrefs @Inject constructor(
  @ApplicationContext context: Context
) : ICurrencyPrefs {
  
  private val prefs = context.applicationContext.getSharedPreferences(
    CURRENCY_PREFS_FILE_NAME,
    Context.MODE_PRIVATE
  )
  
  override var accessKey: String
    get() = prefs.getString(PREFS_ACCESS_KEY, null) ?: "340452cc11802fff3b60a2f72c23ba55"
    set(value) {
      prefs.edit {
        putString(PREFS_ACCESS_KEY, value)
      }
    }
  
  companion object {
    private const val CURRENCY_PREFS_FILE_NAME = "currency_application_prefs"
    
    private const val PREFS_ACCESS_KEY = "currency.pref.access_key"
  }
}