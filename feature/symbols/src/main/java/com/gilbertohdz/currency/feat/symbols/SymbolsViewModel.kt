package com.gilbertohdz.currency.feat.symbols

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor.Result as GetSymbolsResult
import com.gilbertohdz.currency.lib.utils.providers.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class SymbolsViewModel @Inject constructor(
  private val getSymbolsInteractor: GetSymbolsInteractor,
  private val scheduler: SchedulerProvider,
  private val disposable: CompositeDisposable
) : ViewModel() {
  
  private val _symbolsLoading = MutableLiveData<Boolean>()
  private val _symbolsFailure = MutableLiveData<SymbolFailureUi>()
  private val _symbolsError = MutableLiveData<SymbolErrorUi>()
  private val _symbols = MutableLiveData<List<CurrencySymbolUi>>()
  
  fun isSymbolsLoading(): LiveData<Boolean> = _symbolsLoading
  fun isSymbolsFailure(): LiveData<SymbolFailureUi> = _symbolsFailure
  fun isSymbolsError(): LiveData<SymbolErrorUi> = _symbolsError
  fun symbols(): LiveData<List<CurrencySymbolUi>> = _symbols
  
  fun getSymbols() {
    Observable.just(GetSymbolsInteractor.Params)
      .compose(getSymbolsInteractor.getTransformer())
      .startWith(GetSymbolsInteractor.Result.InProgress)
      .subscribeOn(scheduler.io())
      .observeOn(scheduler.ui())
      .subscribe(this::symbolsSubscribe)
      .addTo(disposable)
  }
  
  private fun symbolsSubscribe(result: GetSymbolsResult) {
    _symbolsLoading.value = result is GetSymbolsResult.InProgress
    
    if (result is GetSymbolsResult.Failed) {
      _symbolsFailure.value = SymbolFailureUi(result.errorTypeCommon)
    }
    
    if (result is GetSymbolsResult.Error) {
      _symbolsError.value = SymbolErrorUi(result.code, result.info)
    }
    
    if (result is GetSymbolsResult.Success) {
      val mapSymbols = result.symbols.map {
        CurrencySymbolUi(symbol = it.key, description = it.value)
      }
  
      _symbols.value = mapSymbols
    }
  }
  
  
  override fun onCleared() {
    disposable.clear()
    super.onCleared()
  }
}