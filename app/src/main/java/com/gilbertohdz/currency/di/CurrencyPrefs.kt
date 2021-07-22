package com.gilbertohdz.currency.di

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.gilbertohdz.currency.lib.utils.prefs.ICurrencyPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyPrefs @Inject constructor(
  @ApplicationContext context: Context
) : ICurrencyPrefs {
  
  private val prefs = context.applicationContext.getSharedPreferences(
    CURRENCY_PREFS_FILE_NAME,
    Context.MODE_PRIVATE
  )
  
  override var accessKey: String
    get() {
      pingSession()
      return if (isSessionExpired()) {
        "expired_key"
      } else {
        prefs.getString(PREFS_ACCESS_KEY, null) ?: "340452cc11802fff3b60a2f72c23ba55"
      }
    }
    set(value) {
      prefs.edit {
        putString(PREFS_ACCESS_KEY, value)
      }
    }
  
  override fun isSessionExpired(): Boolean {
    val sessionTime = prefs.getLong(PREFS_REMAINING_TIME_KEY, 0)
    val timeToMatch = Calendar.getInstance().timeInMillis
    return (timeToMatch > sessionTime)
  }
  
  override fun restartSession() {
    prefs.edit {
      putLong(PREFS_REMAINING_TIME_KEY, 0)
    }
  }
  
  private fun pingSession() {
    val time = prefs.getLong(PREFS_REMAINING_TIME_KEY, 0)
    if (time <= 0) {
      prefs.edit {
        val sessionTime = Calendar.getInstance()
        sessionTime.add(Calendar.MINUTE, ACTIVE_SESSION_IN_MINUTE)
        putLong(PREFS_REMAINING_TIME_KEY, sessionTime.timeInMillis)
      }
    }
  }
  
  
  
  companion object {
    private const val ACTIVE_SESSION_IN_MINUTE = 60
    
    private const val CURRENCY_PREFS_FILE_NAME = "currency_application_prefs"
    
    private const val PREFS_ACCESS_KEY = "currency.pref.access_key"
    private const val PREFS_REMAINING_TIME_KEY = "currency.pref.remaining-time"
  }
}