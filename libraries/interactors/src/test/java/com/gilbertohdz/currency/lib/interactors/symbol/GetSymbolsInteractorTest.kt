package com.gilbertohdz.currency.lib.interactors.symbol

import com.gilbertohdz.currency.lib.api.CurrencyService
import com.gilbertohdz.currency.lib.models.ErrorData
import com.gilbertohdz.currency.lib.models.symbol.SymbolResponse
import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon
import com.gilbertohdz.currency.lib.utils.prefs.ICurrencyPrefs
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

/**
 * Test for [GetSymbolsInteractor]
 */
internal class GetSymbolsInteractorTest {
  
  private val mockCurrencyService: CurrencyService = mockk()
  private val mockPrefs: ICurrencyPrefs = mockk()
  
  private val interactor = GetSymbolsInteractor(mockCurrencyService, mockPrefs)
  
  @Before
  fun setUp() {
    every { mockPrefs.accessKey } returns KEY_ACCESS
  }
  
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
  fun `error in API response return InProgress then Error`() {
    val response = mockSymbolErrorResponse()
    
    every {
      mockCurrencyService.getSymbols(KEY_ACCESS)
    } returns Single.just(response)
    
    val result = Observable.just(GetSymbolsInteractor.Params)
      .compose(interactor.getTransformer())
      .test()
    
    result.assertValueSequence(listOf(
      GetSymbolsInteractor.Result.InProgress,
      GetSymbolsInteractor.Result.Error(response.error!!.code, response.error?.info ?: response.error?.type ?: "")
    ))
  }
  
  @Test
  fun `failure in API response return InProgress then Failure`() {
    val mockErrorResponse = createHttpException(404)
  
    every {
      mockCurrencyService.getSymbols(KEY_ACCESS)
    } returns Single.error(mockErrorResponse)
  
    val result = Observable.just(GetSymbolsInteractor.Params)
      .compose(interactor.getTransformer())
      .test()
  
    result.assertValueSequence(listOf(
      GetSymbolsInteractor.Result.InProgress,
      GetSymbolsInteractor.Result.Failed(mockErrorResponse, ErrorTypeCommon.NETWORK)
    ))
  }
  
  companion object {
    private const val KEY_ACCESS = "KEY_ACCESS"
    private const val KEY_ERROR_INVALID_ACCESS_KEY = 101
  }
  
  private fun createHttpException(code: Int): HttpException {
    return HttpException(Response.error<List<SymbolResponse>>(code, ResponseBody.create(MediaType.parse(""), "")))
  }
  
  private fun mockSymbolSuccessResponse(): SymbolResponse {
    return SymbolResponse(
      success = true,
      symbols = mapOf("A" to "Aaa", "B" to "Bbb"),
      error = null
    )
  }
  
  private fun mockSymbolErrorResponse(): SymbolResponse {
    return SymbolResponse(
      success = false,
      symbols = emptyMap(),
      error = ErrorData(
        code = KEY_ERROR_INVALID_ACCESS_KEY,
        info = "invalid_access_key",
        type = "The current subscription plan does not support this API endpoint."
      )
    )
  }
}