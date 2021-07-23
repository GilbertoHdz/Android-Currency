package com.gilbertohdz.currency.lib.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gilbertohdz.currency.lib.data.dao.RateDao
import com.gilbertohdz.currency.lib.data.dao.SymbolDao
import com.gilbertohdz.currency.lib.entities.RateEntity
import com.gilbertohdz.currency.lib.entities.SymbolEntity

private const val DATABASE_VERSION = 1

@Database(
  entities = [RateEntity::class, SymbolEntity::class],
  version = DATABASE_VERSION,
  exportSchema = false // false until productions being released
)
abstract class CurrencyDatabase : RoomDatabase() {
  
  companion object {
    private const val DATABASE_NAME = "currency_hvzrtl07"
    
    fun buildDatabase(application: Context) : CurrencyDatabase {
      return Room.databaseBuilder(
        application,
        CurrencyDatabase::class.java,
        DATABASE_NAME
      ).build()
    }
  }
  
  abstract fun rateDao(): RateDao
  abstract fun symbolDao(): SymbolDao
}