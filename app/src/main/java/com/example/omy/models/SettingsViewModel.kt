package com.example.omy.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omy.ExchangeRepo
import kotlinx.coroutines.launch

class SettingsViewModel :ViewModel() {
    val symbols =MutableLiveData<List<String>>()
    private val repo = ExchangeRepo


    init {
        viewModelScope.launch {
            symbols.value = repo.getSymbols()
        }
    }
}