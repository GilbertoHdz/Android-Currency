package com.gilbertohdz.currency.lib.interactors.symbol

import com.gilbertohdz.currency.lib.api.CurrencyService
import com.gilbertohdz.currency.lib.interactors.BaseInteractor
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor.Params
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor.Result
import com.gilbertohdz.currency.lib.models.symbol.SymbolResponse
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import javax.inject.Inject

class GetSymbolsInteractor @Inject constructor(
    private val currencyService: CurrencyService
): BaseInteractor<Params, Result>() {

    override fun getTransformer(): ObservableTransformer<Params, Result> = getSymbols
    
    private val getSymbols : ObservableTransformer<Params, Result> = ObservableTransformer {
        it.flatMap { _ ->
            getSymbols()
                .toObservable()
                .map { result ->
                    Result.Success(result.symbols) as GetSymbolsInteractor.Result
                }
                .onErrorReturn { error -> Result.Failed }
                .startWith(Result.InProgress)
        }
    }
    
    private fun getSymbols(): Single<SymbolResponse> {
        return currencyService.getSymbols("340452cc11802fff3b60a2f72c23ba55")
    }

    object Params

    sealed class Result {
        object InProgress: Result()
        data class Success(val symbols: Map<String, String>): Result()
        object Failed: Result()
    }
}