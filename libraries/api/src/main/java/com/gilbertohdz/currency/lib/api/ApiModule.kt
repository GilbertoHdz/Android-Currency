package com.gilbertohdz.currency.lib.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesCurrencyService(retrofit: Retrofit) : CurrencyService {
        return retrofit.create(CurrencyService::class.java)
    }
}