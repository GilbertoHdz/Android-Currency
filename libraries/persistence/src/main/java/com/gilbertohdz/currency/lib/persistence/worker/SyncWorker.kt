package com.gilbertohdz.currency.lib.persistence.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor
import com.gilbertohdz.currency.lib.utils.providers.SchedulerProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

@HiltWorker
class SyncWorker @AssistedInject constructor(
  @Assisted appContext: Context,
  @Assisted workerParams: WorkerParameters,
  private val getSymbolsInteractor: GetSymbolsInteractor,
  private val scheduler: SchedulerProvider,
  private val disposable: CompositeDisposable
) : Worker(appContext, workerParams) {
  
  override fun doWork(): Result {
    Log.i(SYNC_WORKER_TAG, "doWork()")
    
    disposable.clear()
    Observable.just(GetSymbolsInteractor.Params)
      .compose(getSymbolsInteractor.getTransformer())
      .startWith(GetSymbolsInteractor.Result.InProgress)
      .subscribeOn(scheduler.io())
      .observeOn(scheduler.ui())
      .subscribe({result -> Log.i(SYNC_WORKER_TAG, "sync_result: $result") }, { error -> error.printStackTrace() })
      .addTo(disposable)
    return Result.success()
  }
  
  override fun onStopped() {
    disposable.clear()
    super.onStopped()
  }
  
  companion object {
    const val SYNC_WORKER_TAG = "sync_worker"
    const val SYNC_WORKER_INTERVAL_MINUTES = 15L
  }
}