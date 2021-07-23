package com.gilbertohdz.currency.feat.rates

import androidx.lifecycle.*
import com.gilbertohdz.currency.lib.data.dao.RateDao
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor.Result as GetRatesResult
import com.gilbertohdz.currency.lib.utils.providers.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
  private val getLatestRatesInteractor: GetLatestRatesInteractor,
  private val ratesDao: RateDao,
  private val scheduler: SchedulerProvider,
  private val disposable: CompositeDisposable
) : ViewModel() {
  
  private val _ratesLoading = MutableLiveData<Boolean>()
  private val _ratesFailure = MutableLiveData<RateFailureUi>()
  private val _ratesError = MutableLiveData<RateErrorUi>()
  private val _rates = MutableLiveData<List<RatesUi>>()
  
  fun isRatesLoading(): LiveData<Boolean> = _ratesLoading
  fun isRatesFailure(): LiveData<RateFailureUi> = _ratesFailure
  fun isRatesError(): LiveData<RateErrorUi> = _ratesError
  fun rates(): LiveData<List<RatesUi>> = _rates
  
  fun getStoredRates(currency: String) {
    viewModelScope.launch {
      ratesDao.getRatesByBase(currency)
        .collect { result ->
          if (result.isNotEmpty()) {
            _rates.value = result.map {
              RatesUi(it.currency, it.value, DEFAULT_CONVERT_VALUE)
            }
          } else {
            getRates(currency)
          }
        }
    }
  }
  
  fun getRates(currency: String) {
    Observable.just(GetLatestRatesInteractor.Params(currency))
      .compose(getLatestRatesInteractor.getTransformer())
      .startWith(GetRatesResult.InProgress)
      .subscribeOn(scheduler.io())
      .observeOn(scheduler.ui())
      .subscribe(this::ratesSubscribe)
      .addTo(disposable)
  }
  
  private fun ratesSubscribe(result: GetRatesResult) {
    _ratesLoading.value = result is GetRatesResult.InProgress
    
    if (result is GetRatesResult.Error) {
      _ratesError.value = RateErrorUi(result.code, result.info)
    }
    
    if (result is GetRatesResult.Failed) {
      _ratesFailure.value = RateFailureUi(result.errorTypeCommon)
    }
    
    if (result is GetRatesResult.Success) {
      val mapRates = result.rates.map {
        RatesUi(it.key, it.value, DEFAULT_CONVERT_VALUE)
      }
  
      _rates.value = mapRates
    }
  }
  
  fun calculateConversions(newValue: String) {
    val toNewValue = if (newValue.isEmpty()) DEFAULT_CONVERT_VALUE else newValue.toInt()
    val temp = _rates.value?.map { it.copy(toConvert = toNewValue) } ?: emptyList()
    _rates.value = temp
  }
  
  override fun onCleared() {
    disposable.clear()
    super.onCleared()
  }
  
  companion object {
    private const val DEFAULT_CONVERT_VALUE = 1
  }
}