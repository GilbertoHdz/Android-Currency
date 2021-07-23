package com.gilbertohdz.currency.lib.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gilbertohdz.currency.lib.entities.RateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addRate(rateEntity: RateEntity)
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAllRates(rateEntity: List<RateEntity>)
  
  @Query("SELECT * FROM rates")
  fun getRates(): Flow<List<RateEntity>>
}