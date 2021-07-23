package com.gilbertohdz.currency.lib.data.dao

import androidx.room.*
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
  
  @Query("DELETE FROM symbols")
  fun deleteAll()
}