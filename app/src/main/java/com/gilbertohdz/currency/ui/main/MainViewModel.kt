package com.gilbertohdz.currency.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor
import com.gilbertohdz.currency.lib.utils.providers.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val getSymbolsInteractor: GetSymbolsInteractor,
  private val scheduler: SchedulerProvider,
  private val disposable: CompositeDisposable
) : ViewModel() {
  
  fun getSymbols() {
    Observable.just(GetSymbolsInteractor.Params)
      .compose(getSymbolsInteractor.getTransformer())
      .startWith(GetSymbolsInteractor.Result.InProgress)
      .subscribeOn(scheduler.io())
      .observeOn(scheduler.ui())
      .subscribe(this::symbolsSubscribe)
      .addTo(disposable)
  }
  
  private fun symbolsSubscribe(result: GetSymbolsInteractor.Result) {
    Log.i("GIL", result.toString())
  }
  
  
  override fun onCleared() {
    disposable.clear()
    super.onCleared()
  }
}