package com.gilbertohdz.currency.lib.component.extension.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(
  @LayoutRes layoutRes: Int,
  attatchToRoot: Boolean = true
) : View = LayoutInflater.from(context).inflate(layoutRes, this, attatchToRoot)