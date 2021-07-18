package com.gilbertohdz.currency.lib.interactors.latest

import com.gilbertohdz.currency.lib.api.CurrencyService
import com.gilbertohdz.currency.lib.interactors.BaseInteractor
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor.Params
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor.Result
import com.gilbertohdz.currency.lib.models.rates.RateResponse
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import javax.inject.Inject

class GetLatestRatesInteractor @Inject constructor(
    private val currencyService: CurrencyService
): BaseInteractor<Params, Result>() {

    override fun getTransformer(): ObservableTransformer<Params, Result> = getRates
    
    private val getRates : ObservableTransformer<Params, Result> = ObservableTransformer {
        it.flatMap { params ->
            getRates(params)
                .toObservable()
                .map { result ->
                    Result.Success(result.rates) as GetLatestRatesInteractor.Result
                }
                .onErrorReturn { Result.Failed }
                .startWith(Result.InProgress)
        }
    }
    
    private fun getRates(params: Params): Single<RateResponse> {
        return currencyService.getLatestByBase("340452cc11802fff3b60a2f72c23ba55", params.base)
    }

    data class Params(
        val base: String
    )

    sealed class Result {
        object InProgress: Result()
        data class Success(val rates: Map<String, Double>): Result()
        object Failed: Result()
    }
}