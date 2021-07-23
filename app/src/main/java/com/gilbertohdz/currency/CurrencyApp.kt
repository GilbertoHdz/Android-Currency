package com.gilbertohdz.currency

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.gilbertohdz.currency.lib.persistence.worker.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CurrencyApp : Application(), Configuration.Provider {
  
  @Inject lateinit var workerFactory: HiltWorkerFactory
  
  override fun onCreate() {
    super.onCreate()
    createPeriodicWorkSyncRequest()
  }
  
  override fun getWorkManagerConfiguration(): Configuration {
    return Configuration.Builder()
      .setMinimumLoggingLevel(android.util.Log.DEBUG)
      .setWorkerFactory(workerFactory)
      .build()
  }
  
  private fun createPeriodicWorkSyncRequest() {
    val constraints = Constraints.Builder()
      .setRequiresCharging(true)
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()
    
    val periodicWorkRequest = PeriodicWorkRequest.Builder(
      SyncWorker::class.java,
      SyncWorker.SYNC_WORKER_INTERVAL_MINUTES,
      TimeUnit.MINUTES,
      SyncWorker.SYNC_WORKER_INTERVAL_MINUTES,
      TimeUnit.MINUTES
    ).
    addTag(SyncWorker.SYNC_WORKER_TAG).
    setConstraints(constraints).
    build()
    
    val workManager = WorkManager.getInstance(this)
    workManager.enqueue(periodicWorkRequest)
  
  }
}