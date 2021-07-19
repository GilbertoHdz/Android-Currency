package com.gilbertohdz.currency.feat.rates

import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon

data class RatesUi(
  val currency: String,
  val value: Double,
  val toConvert: Int
) {
  fun convertToValue(): Double {
    return this.value * this.toConvert
  }
}

data class RateErrorUi(
  val code: Int,
  val description: String
)

data class RateFailureUi(
  val typeError: ErrorTypeCommon
)