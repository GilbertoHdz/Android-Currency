package com.gilbertohdz.currency.lib.component.views

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.gilbertohdz.currency.lib.component.R

fun Context.showDialogSession(accept: (Unit) -> Unit) {
  val builder = AlertDialog.Builder(this)
  builder.apply {
    setTitle(R.string.component_dialog_restart_session_title)
    setMessage(R.string.component_dialog_restart_session_desc)
    setCancelable(false)
    setPositiveButton(R.string.component_dialog_restart_session_ok) { _, _ ->
      accept.invoke(Unit)
    }
    setNegativeButton(R.string.component_dialog_restart_session_cancel, null)
  }
  builder.create()
  builder.show()
}