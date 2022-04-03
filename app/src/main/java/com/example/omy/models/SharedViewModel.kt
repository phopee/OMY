package com.example.omy.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omy.ExchangeRepo
import com.example.omy.entities.ExchangeRate
import com.example.omy.network.Service
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SharedViewModel :ViewModel() {
 var BASE = MutableLiveData("EUR")
 var REFRESH_RATE = MutableLiveData(5000.0)
 private val repo = ExchangeRepo


 fun changeRefreshRate(){
  repo.setRefreshRate(REFRESH_RATE.value!!.toLong())
 }

}




