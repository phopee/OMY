package com.example.omy.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omy.ExchangeRepo
import com.example.omy.Utils
import com.example.omy.entities.ExchangeRate
import com.example.omy.network.Service
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.Util
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class HistoricalRatesViewModel : ViewModel() {
    val ratesList = MutableLiveData<Map<String, ExchangeRate>>()
    val data = MutableLiveData<Map<String, Double>>()
    private val repo = ExchangeRepo

    init {

        viewModelScope.launch {
            ratesList.value = repo.getHistoricalRates()
        }
    }
}






