package com.example.omy

import com.example.omy.entities.ExchangeRate
import com.example.omy.network.IExchangeApiService
import com.example.omy.network.Service
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object ExchangeRepo {
    var refreshIntervalMs: Long = 5000


    val latestRatesFlow: Flow<Response<ExchangeRate>> = flow {
            while(true) {
                val rates = Service.api.getRatesAsync()
                if(rates.isSuccessful) {
                    emit(rates)
                    println("andreea" + rates.body().toString())
                }
                else{
                    println(rates.message())
                }
                delay(getRefreshRate()) // Suspends the coroutine for some time
            }
        }

    suspend fun getHistoricalRates() : MutableMap<String, ExchangeRate>{
        val period = getDaysAgo()
        val temp  = mutableMapOf<String,ExchangeRate>()
        for (p in period) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatted = p.format(formatter)
            val resp =
                Service.api.getHistoricalRates(formatted.toString(), "EUR", "RON,USD,BGN")

            if (resp.isSuccessful && resp.body()?.rates != null && resp.body()?.date != null) {

                val mapKey = resp.body()!!.date
                val newValue = resp.body()!!
                temp[mapKey] = newValue
                println(resp.body()?.rates.toString() + "andreea")
            } else {
                println(resp.message())
            }

        }
        return temp
    }
    fun getDaysAgo(): ArrayList<LocalDateTime> {
        val arr = ArrayList<LocalDateTime>()
        for(i in 0..10) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            val date = calendar.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            arr.add(date)
        }

        return arr
    }

     fun switchBase(
        base: String?,
        map: Map<String, Double>?,
        newBase: String?
    ): HashMap<String, Double>? {
        val newMap: HashMap<String, Double> = HashMap()
        var exchangeRate: Double? = null
        if (null != base && null != map && null != newBase) {
            for ((k, v) in map) {
                if (k.equals(newBase, ignoreCase = true)) {
                    exchangeRate = v
                }
            }
            if (null != exchangeRate) {
                // RON -> base
                // EUR -> newBase
                // 4,943 -> exchangeRate
                // 1 EUR = 4,943 RON
                // => 1 RON = 0.2023 EUR  (EUR in newBase) (1/4,943 = 0.2023)
                val newRate = 1 / exchangeRate
                // Add the key-value pair for the initial base
                newMap[base] = newRate
                // Now we compute for dollar
                // 1 EUR = 1.104 USD
                for ((k, v) in map) {
                    if (k != newBase) {
                        val newValue = v * newRate
                        newMap[k] = newValue
                    }
                }
            }
        }
        return newMap
    }
    fun getRefreshRate():Long{
        return this.refreshIntervalMs
    }
    fun setRefreshRate(value: Long){
        this.refreshIntervalMs = value
    }
    suspend fun getSymbols():MutableList<String>? {
        val resp = Service.api.getSymbols()
        if (resp.isSuccessful && !resp.body()?.symbols.isNullOrEmpty()) {
            return resp.body()!!.symbols.keys.filterNotNull().toMutableList()
        }
        return null
    }


}
