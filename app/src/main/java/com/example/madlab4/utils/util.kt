package com.example.madlab4.utils

import android.app.Dialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.sql.RowId

enum class Status{
    SUCCESS,
    ERROR,
    LOADING
}
fun Context.longToastShow(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}
fun Dialog.setupDialog(layoutResId: Int) {
    setContentView(layoutResId)
    window!!.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    setCancelable(false)
}

fun validateEditText(editText: TextInputEditText, textTextInputLayout: TextInputLayout): Boolean {
    return when {
        editText.text.toString().trim().isEmpty() -> {
            textTextInputLayout.error = "Required"
            false
        }

        else -> {
            textTextInputLayout.error = null
            true
        }
    }
}

fun clearEditText(editText: TextInputEditText, textTextInputLayout: TextInputLayout) {
    editText.text = null
    textTextInputLayout.error = null

}

