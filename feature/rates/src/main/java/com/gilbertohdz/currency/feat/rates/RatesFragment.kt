package com.gilbertohdz.currency.feat.rates

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.rates_fragment.*

@AndroidEntryPoint
class RatesFragment : Fragment() {
  
  private val viewModel by viewModels<RatesViewModel>()
  
  private val arg: RatesFragmentArgs by navArgs()
  
  private val ratesAdapter by lazy { RatesAdapter() }
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.rates_fragment, container, false)
  }
  
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    (activity as AppCompatActivity).setSupportActionBar(ratesToolbar)
    (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
  
    ratesToolbar.setNavigationOnClickListener {
      Log.i("GIL", arg.symbol)
    }
  
    viewModel.getRates(arg.symbol)
    viewModel.rates().observe(viewLifecycleOwner, Observer { result -> ratesAdapter.submitList(result) })
    bindView()
  }
  
  private fun bindView() {
    ratesRecyclerView.apply {
      layoutManager = LinearLayoutManager(
        requireContext(),
        LinearLayoutManager.VERTICAL,
        false
      )
      adapter = ratesAdapter
    }
  }
  
  
}