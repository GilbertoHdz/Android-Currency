package com.gilbertohdz.currency.lib.models.symbol

import com.gilbertohdz.currency.lib.models.ErrorData

data class SymbolResponse (
    val success: Boolean,
    val symbols: Map<String, String>,
    val error: ErrorData?
)