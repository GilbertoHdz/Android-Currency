package com.gilbertohdz.currency.lib.interactors.latest

import com.gilbertohdz.currency.lib.api.CurrencyService
import com.gilbertohdz.currency.lib.interactors.BaseInteractor
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor.Params
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor.Result
import com.gilbertohdz.currency.lib.models.rates.RateResponse
import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon
import com.gilbertohdz.currency.lib.utils.prefs.ICurrencyPrefs
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import java.lang.IllegalStateException
import javax.inject.Inject

class GetLatestRatesInteractor @Inject constructor(
    private val currencyService: CurrencyService,
    private val prefs: ICurrencyPrefs
): BaseInteractor<Params, Result>() {

    override fun getTransformer(): ObservableTransformer<Params, Result> = getRates
    
    private val getRates : ObservableTransformer<Params, Result> = ObservableTransformer {
        it.flatMap { params ->
            getRates(params)
                .toObservable()
                .map { result ->
                    if (result.success) {
                        Result.Success(result.rates)
                    } else {
                        val error = result.error ?: throw IllegalStateException("Error with code and info shouldn't be null")
                        Result.Error(error.code, error.info ?: error.type)
                    } as GetLatestRatesInteractor.Result
                }
                .onErrorReturn { e -> Result.Failed(e, ErrorTypeCommon.fromThrowable(e)) }
                .startWith(Result.InProgress)
        }
    }
    
    private fun getRates(params: Params): Single<RateResponse> {
        return currencyService.getLatestByBase(prefs.accessKey, params.base)
    }

    data class Params(
        val base: String
    )

    sealed class Result {
        object InProgress: Result()
        data class Success(val rates: Map<String, Double>): Result()
        data class Error(val code: Int, val info: String): Result()
        data class Failed(val e: Throwable, val errorTypeCommon: ErrorTypeCommon): Result()
    }
}