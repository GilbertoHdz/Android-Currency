package com.gilbertohdz.currency.di

import com.gilbertohdz.currency.lib.utils.providers.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
  
  @Binds
  abstract fun bindSchedulerProvider(
    schedulerProviderImpl: SchedulerProviderImpl
  ): SchedulerProvider
}