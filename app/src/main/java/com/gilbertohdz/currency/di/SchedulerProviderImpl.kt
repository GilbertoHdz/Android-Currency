package com.gilbertohdz.currency.di

import com.gilbertohdz.currency.lib.utils.providers.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProviderImpl @Inject constructor(): SchedulerProvider {
  
  override fun computation(): Scheduler {
    return Schedulers.computation()
  }
  
  override fun io(): Scheduler {
    return Schedulers.io()
  }
  
  override fun ui(): Scheduler {
    return AndroidSchedulers.mainThread()
  }
}