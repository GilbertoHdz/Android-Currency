package com.gilbertohdz.currency.feat.rates

import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon
import java.text.NumberFormat
import java.util.*

data class RatesUi(
  val currency: String,
  val value: Double,
  val toConvert: Int
) {
  fun convertToValue(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance(currency)
    format.format((this.value * this.toConvert))
    return format.format((this.value * this.toConvert))
  }
}

data class RateErrorUi(
  val code: Int,
  val description: String
)

data class RateFailureUi(
  val typeError: ErrorTypeCommon
)