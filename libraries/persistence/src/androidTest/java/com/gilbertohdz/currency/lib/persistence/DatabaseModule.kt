package com.gilbertohdz.currency.lib.persistence

import android.app.Application
import com.gilbertohdz.currency.lib.data.dao.RateDao
import com.gilbertohdz.currency.lib.data.dao.SymbolDao
import com.gilbertohdz.currency.lib.persistence.db.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_VERSION = 1

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
  
  @Singleton
  @Provides
  fun provideDatabase(application: Application): CurrencyDatabase {
    return CurrencyDatabase.buildDatabase(application)
  }
  
  @Singleton
  @Provides
  fun provideSymbol(database: CurrencyDatabase): SymbolDao = database.symbolDao()
  
  @Singleton
  @Provides
  fun provideRates(database: CurrencyDatabase): RateDao = database.rateDao()
}