package com.gilbertohdz.currency.feat.symbols

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.gilbertohdz.currency.lib.component.extension.recycler.inflate

class SymbolsAdapter : ListAdapter<CurrencySymbolUi, SymbolViewHolder>(DiffUtilCallback) {
  
  private var symbolsClickHandler: SymbolsClickHandler? = null
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymbolViewHolder {
    return SymbolViewHolder(parent.inflate(R.layout.symbols_item_view, false))
  }
  
  override fun onBindViewHolder(holder: SymbolViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind(item, symbolsClickHandler)
  }
  
  fun onSymbolsClickListener(listener: SymbolsClickHandler) {
    symbolsClickHandler = listener
  }
}

internal typealias SymbolsClickHandler = (String) -> Unit

private object DiffUtilCallback : DiffUtil.ItemCallback<CurrencySymbolUi>() {
  override fun areItemsTheSame(oldItem: CurrencySymbolUi, newItem: CurrencySymbolUi): Boolean {
    return oldItem.symbol == newItem.symbol
  }
  
  override fun areContentsTheSame(oldItem: CurrencySymbolUi, newItem: CurrencySymbolUi): Boolean {
    return oldItem == newItem
  }
}