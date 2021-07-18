package com.gilbertohdz.currency.feat.rates

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rates_item_view.view.*

class RatesVieHolder(
  itemView: View
) : RecyclerView.ViewHolder(itemView) {
  
  fun bind(item: RatesUi) = with(itemView) {
    ratesItemViewCurrency.text = item.currency
    ratesItemViewValue.text = item.convertToValue().toString()
  }
}