package com.example.omy.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omy.ExchangeRepo
import com.example.omy.entities.ExchangeRate
import com.example.omy.network.Service
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@OptIn(InternalCoroutinesApi::class)
class HomeViewModel :ViewModel() {
    val response = MutableLiveData<ExchangeRate>()
    val lastUpdate = MutableLiveData<Long>()
    val ratesList = MutableLiveData<List<Pair<String, Double>>>()

    private val repo = ExchangeRepo

    init {
        ratesList.value = listOf(Pair("RON", 2.0))
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            repo.latestRatesFlow
                .catch { exception -> print(exception) }
                .collect { resp ->
                    response.value = resp.body()
                    println(resp.body()?.rates)
                    }

                }


    }

    fun getListFromResponse(response :ExchangeRate,base: String): List<Pair<String, Double>>{
        if(base == response.base) {
            return response.rates.keys.map { Pair(it, response.rates[it]!!) }
        }else{
            val newBase = repo.switchBase(response.base,response.rates,base)!!
            return newBase.keys.map { Pair(it, newBase[it]!!) }
        }
    }

}