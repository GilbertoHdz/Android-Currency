package com.gilbertohdz.currency.feat.rates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gilbertohdz.currency.lib.component.extension.edittext.afterTextChanged
import com.gilbertohdz.currency.lib.utils.common.ErrorTypeCommon
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
  
    ratesToolbar.setNavigationOnClickListener { findNavController().popBackStack() }
  
    viewModel.getRates(arg.symbol)
    viewModel.rates().observe(viewLifecycleOwner, Observer { result -> bindRates(result) })
    viewModel.isRatesFailure().observe(viewLifecycleOwner, Observer { bindFailureView(it) })
    viewModel.isRatesError().observe(viewLifecycleOwner, Observer { bindErrorView(it) })
    viewModel.isRatesLoading().observe(viewLifecycleOwner, Observer { ratesLoader.isVisible = it })
    bindView()
    setupConversion()
  }
  
  private fun bindRates(result: List<RatesUi>?) {
    result?.let {
      ratesAdapter.submitList(it)
      ratesMainGroup.visibility = View.VISIBLE
    }
  }
  
  private fun bindErrorView(errorUi: RateErrorUi?) {
    errorUi?.let { error ->
      componentMessageCoverViewContainer.title(error.code.toString())
      componentMessageCoverViewContainer.description(error.description)
      componentMessageCoverViewContainer.visibility = View.VISIBLE
    }
  }
  
  private fun bindFailureView(failureUi: RateFailureUi?) {
    failureUi?.let { failure ->
      val failTitle = if (failure.typeError == ErrorTypeCommon.NETWORK) R.string.component_error_connection_title else R.string.component_error_generic_title
      val failDesc = if (failure.typeError == ErrorTypeCommon.NETWORK) R.string.component_error_connection_description else R.string.component_error_generic_description
      
      componentMessageCoverViewContainer.title(getString(failTitle))
      componentMessageCoverViewContainer.description(getString(failDesc))
      componentMessageCoverViewContainer.retryAction { viewModel.getRates(arg.symbol) }
      componentMessageCoverViewContainer.visibility = View.VISIBLE
    }
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
  
  private fun setupConversion() {
    ratesValueEditText.afterTextChanged {
      viewModel.calculateConversions(it)
    }
  }
}