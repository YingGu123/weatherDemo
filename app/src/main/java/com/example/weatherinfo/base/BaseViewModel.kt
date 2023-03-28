package com.example.weatherinfo.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.serviceapi.exception.ServiceException
import com.example.weatherinfo.R
import com.example.weatherinfo.model.UiState
import com.example.weatherinfo.utils.SnackBarUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    var uiStateFlow = MutableStateFlow<UiState>(UiState.Empty)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        val defaultMsg = app.resources.getString(R.string.error_server_msg)
        val msg = when (throwable) {
            is ServiceException -> {
                throwable.message ?: defaultMsg
            }
            else -> {
                defaultMsg
            }
        }
        uiStateFlow.value = UiState.ErrorToast(msg)
    }

    fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler) {
            block()
        }
    }


}