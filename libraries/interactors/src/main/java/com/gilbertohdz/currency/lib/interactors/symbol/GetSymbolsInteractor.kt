package com.gilbertohdz.currency.lib.interactors.symbol

import com.gilbertohdz.currency.lib.api.CurrencyService
import com.gilbertohdz.currency.lib.interactors.BaseInteractor
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor.Params
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor.Result
import com.gilbertohdz.currency.lib.models.symbol.SymbolResponse
import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon
import com.gilbertohdz.currency.lib.utils.prefs.ICurrencyPrefs
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import java.lang.IllegalStateException
import javax.inject.Inject

class GetSymbolsInteractor @Inject constructor(
    private val currencyService: CurrencyService,
    private val prefs: ICurrencyPrefs
): BaseInteractor<Params, Result>() {

    override fun getTransformer(): ObservableTransformer<Params, Result> = getSymbols
    
    private val getSymbols : ObservableTransformer<Params, Result> = ObservableTransformer {
        it.flatMap { _ ->
            getSymbols()
                .toObservable()
                .map { result ->
                    if (result.success) {
                        Result.Success(result.symbols)
                    } else {
                        val error = result.error ?: throw IllegalStateException("Error with code and info shouldn't be null")
                        Result.Error(error.code, error.info ?: error.type)
                    } as GetSymbolsInteractor.Result
                }
                .onErrorReturn { e -> Result.Failed(e, ErrorTypeCommon.fromThrowable((e))) }
                .startWith(Result.InProgress)
        }
    }
    
    private fun getSymbols(): Single<SymbolResponse> {
        return currencyService.getSymbols(prefs.accessKey)
    }

    object Params

    sealed class Result {
        object InProgress: Result()
        data class Success(val symbols: Map<String, String>): Result()
        data class Error(val code: Int, val info: String): Result()
        data class Failed(val e: Throwable, val errorTypeCommon: ErrorTypeCommon): Result()
    }
}