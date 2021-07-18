package com.gilbertohdz.currency.feat.rates

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.gilbertohdz.currency.lib.component.extension.recycler.inflate


class RatesAdapter : ListAdapter<RatesUi, RatesVieHolder>(DiffUtilCallback) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesVieHolder {
    return RatesVieHolder(parent.inflate(R.layout.rates_item_view, false))
  }
  
  override fun onBindViewHolder(holder: RatesVieHolder, position: Int) {
    val item = getItem(position)
    holder.bind(item)
  }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<RatesUi>() {
  override fun areItemsTheSame(oldItem: RatesUi, newItem: RatesUi): Boolean {
    return oldItem.currency == newItem.currency && oldItem.toConvert == oldItem.toConvert
  }
  
  override fun areContentsTheSame(oldItem: RatesUi, newItem: RatesUi): Boolean {
    return oldItem == newItem
  }
}