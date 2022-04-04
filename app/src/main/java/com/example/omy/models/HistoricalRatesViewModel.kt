package com.example.omy.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omy.ExchangeRepo
import com.example.omy.Utils
import com.example.omy.entities.ExchangeRate
import com.example.omy.network.Service
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
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
    fun getChartDataForCurrency(curr :String): LineDataSet{
        val final = ArrayList<Double>()
        val initialList =ratesList.value?.values
        if (initialList != null) {
            for (i in 0..initialList.size - 1){
                val x=   initialList.elementAt(i).rates.filter { it.key.equals(curr,true)}
                final.add(x.get(curr)!!)
            }
        }


        val valueSet1 = ArrayList<Entry>()
        for(i in 0..final.size-1) {
            valueSet1.add(Entry(i.toFloat(),final[i].toFloat()))
        }
        return LineDataSet(valueSet1, curr)
    }
}






