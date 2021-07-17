package com.gilbertohdz.currency.lib.interactors.latest

import com.gilbertohdz.currency.lib.api.CurrencyService
import com.gilbertohdz.currency.lib.interactors.BaseInteractor
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor.Params
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor.Result
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class GetLatestRatesInteractor @Inject constructor(
    private val currencyService: CurrencyService
): BaseInteractor<Params, Result>() {

    override fun getTransformer(): ObservableTransformer<Params, Result> {
        TODO("Not yet implemented")
    }

    object Params

    sealed class Result {
        object InProgress: Result()
        object Success: Result()
        object Failed: Result()
    }
}