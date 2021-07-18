package com.gilbertohdz.currency.feat.rates

data class RatesUi(
  val currency: String,
  val value: Double,
  val toConvert: Int
) {
  fun convertToValue(): Double {
    return this.value * this.toConvert
  }
}