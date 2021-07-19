package com.gilbertohdz.currency.lib.component.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.gilbertohdz.currency.lib.component.R

class CoverMessagesView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
  
  private var clicksHandler: CoverMessagesViewClicks? = null
  private val _coverViewTitle: TextView
  private val _coverViewDescription: TextView
  private val _coverViewRetryAction: Button
  
  init {
    LayoutInflater.from(context).inflate(R.layout.component_message_cover_view, this, true)
    _coverViewTitle = findViewById(R.id.componentFailureCoverViewTitle)
    _coverViewDescription = findViewById(R.id.componentFailureCoverViewDescription)
    _coverViewRetryAction = findViewById(R.id.componentFailureCoverViewRetryAction)
    _coverViewRetryAction.visibility = View.GONE
    _coverViewRetryAction.setOnClickListener { clicksHandler?.let { it.invoke(Unit) } }
  }
  
  fun title(title: String) {
    _coverViewTitle.text = title
  }
  
  fun description(desc: String) {
    _coverViewDescription.text = desc
  }
  
  fun retryAction(listener: CoverMessagesViewClicks) {
    clicksHandler = listener
    _coverViewRetryAction.visibility = View.VISIBLE
  }
}

internal typealias CoverMessagesViewClicks = (Unit) -> Unit
