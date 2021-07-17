package com.gilbertohdz.currency.lib.models.rates

import com.gilbertohdz.currency.lib.models.ErrorData

data class RateResponse(
    val success: Boolean,
    val base: String,
    val rates: Map<String, Double>,
    val error: ErrorData
)