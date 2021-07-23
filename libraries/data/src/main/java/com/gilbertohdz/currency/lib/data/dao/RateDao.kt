package com.gilbertohdz.currency.lib.data.dao

import androidx.room.*
import com.gilbertohdz.currency.lib.entities.RateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addRate(rateEntity: RateEntity)
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAllRates(rateEntity: List<RateEntity>)
  
  @Query("SELECT * FROM rates WHERE baseId = :base")
  fun getRatesByBase(base: String): Flow<List<RateEntity>>
}