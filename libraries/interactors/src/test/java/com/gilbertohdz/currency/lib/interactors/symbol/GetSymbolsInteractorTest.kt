package com.gilbertohdz.currency.lib.interactors.symbol

import com.gilbertohdz.currency.lib.api.CurrencyService
import com.gilbertohdz.currency.lib.models.symbol.SymbolResponse
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test

/**
 * Test for [GetSymbolsInteractor]
 */
internal class GetSymbolsInteractorTest {
  
  private val mockCurrencyService: CurrencyService = mockk()
  
  private val interactor = GetSymbolsInteractor(mockCurrencyService)
  
  @Test
  fun `success in API response return InProgress then Success`() {
    val response = mockSymbolSuccessResponse()
    
    every {
      mockCurrencyService.getSymbols(KEY_ACCESS)
    } returns Single.just(response)
    
    val result = Observable.just(GetSymbolsInteractor.Params)
      .compose(interactor.getTransformer())
      .test()
    
    result.assertValueSequence(listOf(
      GetSymbolsInteractor.Result.InProgress,
      GetSymbolsInteractor.Result.Success(response.symbols)
    ))
  }
  
  @Test
  fun `failure in API response return InProgress then Failure`() {
    val mockError = Throwable()
  
    every {
      mockCurrencyService.getSymbols(KEY_ACCESS)
    } returns Single.error(mockError)
  
    val result = Observable.just(GetSymbolsInteractor.Params)
      .compose(interactor.getTransformer())
      .test()
  
    result.assertValueSequence(listOf(
      GetSymbolsInteractor.Result.InProgress,
      GetSymbolsInteractor.Result.Failed
    ))
  }
  
  companion object {
    private const val KEY_ACCESS = "KEY_ACCESS"
  }
  
  private fun mockSymbolSuccessResponse(): SymbolResponse {
    return SymbolResponse(
      success = true,
      symbols = mapOf("A" to "Aaa", "B" to "Bbb"),
      error = null
    )
  }
}