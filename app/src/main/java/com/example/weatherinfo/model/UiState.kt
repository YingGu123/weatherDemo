package com.example.weatherinfo.model

sealed class UiState {
    object Success : UiState()
    data class ErrorToast(val message: String) : UiState()
    object ErrorPage : UiState()
    object Loading : UiState()
    object Empty : UiState()
}