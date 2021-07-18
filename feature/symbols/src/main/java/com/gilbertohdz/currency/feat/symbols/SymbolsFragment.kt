package com.gilbertohdz.currency.feat.symbols

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.symbols_component_search_toolbar.*
import kotlinx.android.synthetic.main.symbols_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SymbolsFragment : Fragment() {
  
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
    viewModel.symbols().observe(viewLifecycleOwner, Observer { result -> symbolsAdapter.submitList(result) })
    
    bindView()
    setupActionBar()
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
        search.queryHint = "Search currency" /// requireContext().getString()
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
          override fun onQueryTextSubmit(query: String?): Boolean { return false }
          override fun onQueryTextChange(newText: String?): Boolean {
            Log.i("GIL", "searchView: $newText")
            return true
          }
        })
        search.setOnCloseListener {
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
        symbolsHomeAppBar.setExpanded(true)
      }
    }
  }
  
  companion object {
    private const val ANIM_TOOLBAR_DISPLAYED_MAX = 200L
    private const val ANIM_TOOLBAR_DISPLAYED_MIN = 50L
  }
}
