package com.gilbertohdz.currency.lib.models

data class ErrorData(
    val code: Int,
    val info: String?,
    val type: String,
)