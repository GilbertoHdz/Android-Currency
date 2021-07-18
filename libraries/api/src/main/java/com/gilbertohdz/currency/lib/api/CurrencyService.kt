package com.gilbertohdz.currency.lib.api

import com.gilbertohdz.currency.lib.models.rates.RateResponse
import com.gilbertohdz.currency.lib.models.symbol.SymbolResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
  
  /**
   * @EndPoint Supported Symbols
   * access_key: String
   * @return Map<String, String>
   */
  @GET("symbols")
  fun getSymbols(@Query("access_key") accessKey: String): Single<SymbolResponse>
  
  /**
   * @EndPoint Latest Rates
   * access_key: String
   * base: String
   * @return Map<String, Double>
   */
  @GET("latest")
  fun getLatestByBase(
    @Query("access_key") accessKey: String,
    @Query("base") base: String
  ): Single<RateResponse>
}