package com.gilbertohdz.currency.feat.symbols

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.symbols_item_view.view.*

class SymbolViewHolder(
  itemView: View
) : RecyclerView.ViewHolder(itemView) {
  fun bind(
    item: CurrencySymbolUi,
    listener: SymbolsClickHandler?
  ) = with(itemView) {
    symbolsItemViewSymbolCode.text = item.symbol
    symbolsItemViewSymbolDescription.text = item.description
    
    setOnClickListener { listener?.invoke(item.symbol) }
  }
}