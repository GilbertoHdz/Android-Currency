package com.gilbertohdz.currency.feat.symbols

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.symbols_fragment.*

@AndroidEntryPoint
class SymbolsFragment : Fragment() {
  
  private val viewModel by viewModels<SymbolsViewModel>()
  
  private val symbolsAdapter by lazy { SymbolsAdapter() }
  
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
}