package com.gilbertohdz.currency.lib.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gilbertohdz.currency.lib.entities.SymbolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SymbolDao {
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addSymbol(symbolEntity: SymbolEntity)
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAllSymbols(symbols: List<SymbolEntity>)
  
  @Query("SELECT * FROM symbols")
  fun getSymbols(): Flow<List<SymbolEntity>>
}