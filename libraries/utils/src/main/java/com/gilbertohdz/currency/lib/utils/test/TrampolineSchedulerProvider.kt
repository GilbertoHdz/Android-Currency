package com.gilbertohdz.currency.lib.utils.test

import com.gilbertohdz.currency.lib.utils.providers.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Fake implementation of the [SchedulerProvider] interface. Uses a trampoline scheduler for all
 * task types to allow easy testing of Rx Subscriptions.
 */
class TrampolineSchedulerProvider : SchedulerProvider {
  
  override fun computation(): Scheduler {
    return Schedulers.trampoline()
  }
  
  override fun io(): Scheduler {
    return Schedulers.trampoline()
  }
  
  override fun ui(): Scheduler {
    return Schedulers.trampoline()
  }
}
