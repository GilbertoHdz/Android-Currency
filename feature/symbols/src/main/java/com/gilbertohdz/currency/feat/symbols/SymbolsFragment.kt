package com.gilbertohdz.currency.feat.symbols

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.view.ActionMode
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gilbertohdz.currency.lib.component.extension.edittext.onQueryTextChange
import com.gilbertohdz.currency.lib.component.views.showDialogSession
import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon
import com.gilbertohdz.currency.lib.utils.event.Event
import com.gilbertohdz.currency.lib.utils.prefs.ICurrencyPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.symbols_component_search_toolbar.*
import kotlinx.android.synthetic.main.symbols_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SymbolsFragment : Fragment() {
  
  @Inject lateinit var prefs: ICurrencyPrefs
  
  private val viewModel by viewModels<SymbolsViewModel>()
  
  private val symbolsAdapter by lazy { SymbolsAdapter() }
  
  var mActionMode: ActionMode? = null
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.symbols_fragment, container, false)
  }
  
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel.getSymbols()
    viewModel.getStoredSymbols()
    viewModel.symbols().observe(viewLifecycleOwner, Observer { result -> bindSymbols(result) })
    viewModel.isSymbolsFailure().observe(viewLifecycleOwner, Observer { bindFailureView(it) })
    viewModel.isSymbolsError().observe(viewLifecycleOwner, Observer { bindErrorView(it) })
    viewModel.isSymbolsLoading().observe(viewLifecycleOwner, Observer { symbolsLoader.isVisible = it })
    
    bindView()
  }
  
  private fun bindSymbols(result: List<CurrencySymbolUi>?) {
    result?.let {
      symbolsAdapter.submitList(it)
      symbolsRecyclerview.visibility = View.VISIBLE
      symbolsMessageCoverViewContainer.visibility = View.GONE
      setupActionBar()
    }
  }
  
  private fun bindErrorView(errorUi: Event<SymbolErrorUi>) {
    errorUi.getContentIfNotHandled()?.let { error ->
      
      if (error.code == 101 && prefs.isSessionExpired()) {
        requireContext().showDialogSession {
          prefs.restartSession()
          viewModel.getSymbols()
        }
      } else {
        symbolsMessageCoverViewContainer.title(error.code.toString())
        symbolsMessageCoverViewContainer.description(error.description)
        symbolsMessageCoverViewContainer.visibility = View.VISIBLE
      }
    }
  }
  
  private fun bindFailureView(failureUi: SymbolFailureUi?) {
    failureUi?.let { failure ->
      val failTitle = if (failure.typeError == ErrorTypeCommon.NETWORK) R.string.component_error_connection_title else R.string.component_error_generic_title
      val failDesc = if (failure.typeError == ErrorTypeCommon.NETWORK) R.string.component_error_connection_description else R.string.component_error_generic_description
      
      symbolsMessageCoverViewContainer.title(getString(failTitle))
      symbolsMessageCoverViewContainer.description(getString(failDesc))
      symbolsMessageCoverViewContainer.retryAction { viewModel.getSymbols() }
      symbolsMessageCoverViewContainer.visibility = View.VISIBLE
    }
  }
  
  override fun onDestroyView() {
    mActionMode?.finish()
    super.onDestroyView()
  }
  
  private fun bindView() {
    symbolsRecyclerview.apply {
      layoutManager = LinearLayoutManager(
        requireContext(),
        LinearLayoutManager.VERTICAL,
        false
      )
      adapter = symbolsAdapter
    }
    symbolsAdapter.onSymbolsClickListener {
      val uriToRates = "currency-app://com.gilbertohdz.currency/rates/rate-view/$it".toUri()
      val deepLinkRequest: NavDeepLinkRequest = NavDeepLinkRequest.Builder
        .fromUri(uriToRates)
        .build()
      findNavController().navigate(deepLinkRequest)
    }
  }
  
  private fun setupActionBar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
    symbolsComponentSearchToolbarLayout.setOnClickListener {
      if (mActionMode == null) {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).startSupportActionMode(actionModeCallbackCompat)
      } else {
        mActionMode?.finish()
      }
    }
  }
  
  private val actionModeCallbackCompat = object : ActionMode.Callback {
    
    override fun onCreateActionMode(
      mode: ActionMode?,
      menu: Menu?
    ): Boolean {
      (requireActivity() as AppCompatActivity).menuInflater.inflate(R.menu.symbols_component_search_menu, menu)
      mActionMode = mode
      lifecycleScope.launch {
        delay(ANIM_TOOLBAR_DISPLAYED_MIN)
        symbolsHomeAppBar.setExpanded(false, true)
      }
      return true
    }
    
    override fun onPrepareActionMode(
      mode: ActionMode?,
      menu: Menu?
    ): Boolean {
      val searchView: SearchView? = menu?.findItem(R.id.symbolsComponentSearchMenuItem)?.actionView as SearchView?
      searchView?.let { search ->
        search.onActionViewExpanded()
        search.queryHint = getString(R.string.symbols_search_currency)
        search.onQueryTextChange { query ->
          val queryLower = query.toLowerCase()
          val filtered = if (query.isEmpty()) {
            viewModel.symbols().value
          } else {
            viewModel.symbols().value?.filter {
              it.description.toLowerCase().contains(queryLower) ||
                  it.symbol.toLowerCase().contains(queryLower)
            }
          }
          symbolsAdapter.submitList(filtered)
        }
        search.setOnCloseListener {
          symbolsAdapter.submitList(viewModel.symbols().value)
          true
        }
      }
      return false
    }
    
    override fun onActionItemClicked(
      mode: ActionMode?,
      item: MenuItem?
    ): Boolean {
      return false
    }
    
    override fun onDestroyActionMode(mode: ActionMode?) {
      mActionMode = null
      lifecycleScope.launch {
        delay(ANIM_TOOLBAR_DISPLAYED_MAX)
        symbolsHomeAppBar?.setExpanded(true)
      }
    }
  }
  
  companion object {
    private const val ANIM_TOOLBAR_DISPLAYED_MAX = 200L
    private const val ANIM_TOOLBAR_DISPLAYED_MIN = 50L
  }
}
