package com.gilbertohdz.currency.feat.symbols

import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon

data class CurrencySymbolUi(
  val symbol: String,
  val description: String
)

data class SymbolErrorUi(
  val code: Int,
  val description: String
)

data class SymbolFailureUi(
  val typeError: ErrorTypeCommon
)