package com.gilbertohdz.currency.lib.interactors.latest

import com.gilbertohdz.currency.lib.api.CurrencyService
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractorTest
import com.gilbertohdz.currency.lib.models.ErrorData
import com.gilbertohdz.currency.lib.models.rates.RateResponse
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
 * Test for [GetLatestRatesInteractor]
 */
internal class GetLatestRatesInteractorTest {
  
  private val mockCurrencyService: CurrencyService = mockk()
  private val mockPrefs: ICurrencyPrefs = mockk()
  
  private val interactor = GetLatestRatesInteractor(mockCurrencyService, mockPrefs)
  
  @Before
  fun setUp() {
    every { mockPrefs.accessKey } returns KEY_ACCESS
  }
  
  @Test
  fun `success in API response return InProgress then Success`() {
    val response = mockRatesSuccessResponse()
    
    every {
      mockCurrencyService.getLatestByBase(KEY_ACCESS, KEY_BASE)
    } returns Single.just(response)
    
    val result = Observable.just(GetLatestRatesInteractor.Params(KEY_BASE))
      .compose(interactor.getTransformer())
      .test()
    
    result.assertValueSequence(listOf(
      GetLatestRatesInteractor.Result.InProgress,
      GetLatestRatesInteractor.Result.Success(response.rates)
    ))
  }
  
  @Test
  fun `error in API response return InProgress then Error`() {
    val response = mockRatesErrorResponse()
  
    every {
      mockCurrencyService.getLatestByBase(KEY_ACCESS, KEY_BASE)
    } returns Single.just(response)
  
    val result = Observable.just(GetLatestRatesInteractor.Params(KEY_BASE))
      .compose(interactor.getTransformer())
      .test()
  
    result.assertValueSequence(listOf(
      GetLatestRatesInteractor.Result.InProgress,
      GetLatestRatesInteractor.Result.Error(response.error!!.code, response.error?.info ?: response.error?.type ?: "")
    ))
  }
  
  @Test
  fun `failure in API response return InProgress then Failure`() {
    val mockErrorResponse = createHttpException(404)
  
    every {
      mockCurrencyService.getLatestByBase(KEY_ACCESS, KEY_BASE)
    } returns Single.error(mockErrorResponse)
  
    val result = Observable.just(GetLatestRatesInteractor.Params(KEY_BASE))
      .compose(interactor.getTransformer())
      .test()
  
    result.assertValueSequence(listOf(
      GetLatestRatesInteractor.Result.InProgress,
      GetLatestRatesInteractor.Result.Failed(mockErrorResponse, ErrorTypeCommon.NETWORK)
    ))
  }
  
  companion object {
    private const val KEY_ACCESS = "KEY_ACCESS"
    private const val KEY_BASE = "MNX"
    private const val KEY_ERROR_INVALID_ACCESS_KEY = 101
  }
  
  private fun createHttpException(code: Int): HttpException {
    return HttpException(Response.error<List<SymbolResponse>>(code, ResponseBody.create(MediaType.parse(""), "")))
  }
  
  private fun mockRatesSuccessResponse(): RateResponse {
    return RateResponse(
      success = true,
      base = KEY_BASE,
      rates = mapOf("A" to 2.0, "B" to 4.0, "C" to 6.0),
      error = null
    )
  }
  
  private fun mockRatesErrorResponse(): RateResponse {
    return RateResponse(
      success = false,
      base = KEY_BASE,
      rates = emptyMap(),
      error = ErrorData(
        code = KEY_ERROR_INVALID_ACCESS_KEY,
        info = "invalid_access_key",
        type = "The current subscription plan does not support this API endpoint."
      )
    )
  }
}