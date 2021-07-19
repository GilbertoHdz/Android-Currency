package com.gilbertohdz.currency.lib.utils.prefs

interface ICurrencyPrefs {
  
  var accessKey: String
  
  fun isSessionExpired(): Boolean
  
  fun restartSession()
}