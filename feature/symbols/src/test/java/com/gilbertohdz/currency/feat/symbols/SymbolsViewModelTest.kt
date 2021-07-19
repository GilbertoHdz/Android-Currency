package com.gilbertohdz.currency.feat.symbols

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gilbertohdz.currency.lib.interactors.symbol.GetSymbolsInteractor
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
 * Tests for [SymbolsViewModel]
 */
internal class SymbolsViewModelTest {
  
  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()
  
  private val mockGetSymbolsInteractor: GetSymbolsInteractor = mockk()
  
  private val viewModel = SymbolsViewModel(
    mockGetSymbolsInteractor,
    TrampolineSchedulerProvider(),
    CompositeDisposable()
  )
  
  @Test
  fun `interactor InProgress symbols show loading indicator`() {
    every {
      mockGetSymbolsInteractor.getTransformer()
    } returns ObservableTransformer {
      it.flatMap { _ ->
        Observable.just(GetSymbolsInteractor.Result.InProgress)
      }
    }
    
    viewModel.getSymbols()
    
    Assert.assertTrue(viewModel.isSymbolsLoading().value ?: false)
  }
  
  @Test
  fun `interactor Success symbols hides loading indicator and show symbols list`() {
    every {
      mockGetSymbolsInteractor.getTransformer()
    } returns ObservableTransformer {
      it.flatMap { _ ->
        Observable.just(GetSymbolsInteractor.Result.Success(emptyMap()))
      }
    }
  
    viewModel.getSymbols()
  
    Assert.assertFalse(viewModel.isSymbolsLoading().value ?: false)
    Assert.assertNotNull(viewModel.symbols())
  }
  
  @Test
  fun `interactor Error hides loading and show cover screen with message`() {
    every {
      mockGetSymbolsInteractor.getTransformer()
    } returns ObservableTransformer {
      it.flatMap { _ ->
        Observable.just(GetSymbolsInteractor.Result.Error(KEY_ERROR_INVALID_ACCESS_KEY, KEY_ERROR_INVALID_ACCESS_TYPE))
      }
    }
  
    viewModel.getSymbols()
  
    Assert.assertFalse(viewModel.isSymbolsLoading().value ?: false)
    Assert.assertNull(viewModel.symbols().value)
    Assert.assertNotNull(viewModel.isSymbolsError().value)
  }
  
  @Test
  fun `interactor Failure hides loading and show cover screen with retry button`() {
    every {
      mockGetSymbolsInteractor.getTransformer()
    } returns ObservableTransformer {
      it.flatMap { _ ->
        Observable.just(GetSymbolsInteractor.Result.Failed(Throwable(), ErrorTypeCommon.OTHER))
      }
    }
  
    viewModel.getSymbols()
  
    Assert.assertFalse(viewModel.isSymbolsLoading().value ?: false)
    Assert.assertNull(viewModel.symbols().value)
    Assert.assertNotNull(viewModel.isSymbolsFailure().value)
  }
  
  companion object {
    private const val KEY_ERROR_INVALID_ACCESS_KEY = 105
    private const val KEY_ERROR_INVALID_ACCESS_TYPE = "The current subscription plan does not support this API endpoint."
  }
}