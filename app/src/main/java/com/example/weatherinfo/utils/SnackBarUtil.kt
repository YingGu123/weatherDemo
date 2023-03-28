package com.example.weatherinfo.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarUtil {

    fun show(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}