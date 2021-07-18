package com.gilbertohdz.currency.lib.utils.common

import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

enum class ErrorTypeCommon {
    NETWORK,
    NETWORK_SECURITY,
    OTHER;

    companion object {

        fun fromThrowable(e: Throwable) : ErrorTypeCommon {
            return when (e) {
                is HttpException -> {
                    // TODO(timber) Handle log
                    return if (e.code() in 400..499) {
                        NETWORK
                    } else
                        OTHER
                }
                is SocketTimeoutException,
                is UnknownHostException,
                is SocketException -> NETWORK
                is UnknownServiceException -> NETWORK_SECURITY
                else -> OTHER
            }
        }
    }
}