package com.gilbertohdz.currency.lib.component.extension.edittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.SearchView

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
  this.addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
    
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
    
    override fun afterTextChanged(editable: Editable?) {
      afterTextChanged.invoke(editable.toString())
    }
  })
}

fun SearchView.onQueryTextChange(onQueryTextChange: (String) -> Unit) {
  this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean { return false }
    override fun onQueryTextChange(newText: String?): Boolean {
      onQueryTextChange.invoke(newText ?: "")
      return true
    }
  })
}