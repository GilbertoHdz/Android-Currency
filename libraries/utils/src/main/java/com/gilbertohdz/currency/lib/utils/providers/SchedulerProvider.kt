package com.gilbertohdz.currency.lib.utils.providers

import io.reactivex.Scheduler

/** Interface to provide different type of Rx Schedulers. */
interface SchedulerProvider {
  fun computation(): Scheduler
  
  fun io(): Scheduler
  
  fun ui(): Scheduler
}