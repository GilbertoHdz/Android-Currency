package com.gilbertohdz.currency.feat.rates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gilbertohdz.currency.lib.interactors.latest.GetLatestRatesInteractor
import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon
import com.gilbertohdz.currency.lib.utils.test.TrampolineSchedulerProvider
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import junit.framework.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [RatesViewModel]
 */
internal class RatesViewModelTest {
  
  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()
  
  private val mockGetLatestRatesInteractor: GetLatestRatesInteractor = mockk()
  
  private val viewModel = RatesViewModel(
    mockGetLatestRatesInteractor,
    TrampolineSchedulerProvider(),
    CompositeDisposable()
  )
  
  @Test
  fun `interactor load latest rates InProgress show loading indicator`() {
    every {
      mockGetLatestRatesInteractor.getTransformer()
    } returns ObservableTransformer {
      it.flatMap { _ -> Observable.just(GetLatestRatesInteractor.Result.InProgress) }
    }
    
    viewModel.getRates(KEY_CURRENCY)
    
    Assert.assertTrue(viewModel.isRatesLoading().value ?: false)
  }
  
  @Test
  fun `interactor load latest rates Success hides loading indicator and show rates`() {
    every {
      mockGetLatestRatesInteractor.getTransformer()
    } returns ObservableTransformer {
      it.flatMap { _ ->
        Observable.just(GetLatestRatesInteractor.Result.Success(emptyMap()))
      }
    }
    
    viewModel.getRates(KEY_CURRENCY)
    
    Assert.assertFalse(viewModel.isRatesLoading().value ?: true)
    Assert.assertNotNull(viewModel.rates().value)
  }
  
  @Test
  fun `interactor Error hides loading indicator and show cover screen with message`() {
    every {
      mockGetLatestRatesInteractor.getTransformer()
    } returns ObservableTransformer {
      it.flatMap { _ ->
        Observable.just(GetLatestRatesInteractor.Result.Error(KEY_ERROR_INVALID_ACCESS_KEY, KEY_ERROR_INVALID_ACCESS_TYPE))
      }
    }
  
    viewModel.getRates(KEY_CURRENCY)
  
    Assert.assertFalse(viewModel.isRatesLoading().value ?: true)
    Assert.assertNull(viewModel.rates().value)
    Assert.assertNotNull(viewModel.isRatesError().value)
  }
  
  @Test
  fun `interactor Failure hides loading indicator and show cover screen with retry button`() {
    every {
      mockGetLatestRatesInteractor.getTransformer()
    } returns ObservableTransformer {
      it.flatMap { _ ->
        Observable.just(GetLatestRatesInteractor.Result.Failed(Throwable(), ErrorTypeCommon.OTHER))
      }
    }
  
    viewModel.getRates(KEY_CURRENCY)
  
    Assert.assertFalse(viewModel.isRatesLoading().value ?: true)
    Assert.assertNull(viewModel.rates().value)
    Assert.assertNull(viewModel.isRatesError().value)
    Assert.assertNotNull(viewModel.isRatesFailure().value)
  }
  
  companion object {
    private const val KEY_CURRENCY = "MNX"
    private const val KEY_ERROR_INVALID_ACCESS_KEY = 105
    private const val KEY_ERROR_INVALID_ACCESS_TYPE = "The current subscription plan does not support this API endpoint."
  }
 }