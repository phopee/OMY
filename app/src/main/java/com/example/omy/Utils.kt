package com.example.omy

import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class Utils {
    fun getLastTenDays() :ArrayList<Calendar>{
        val dates = ArrayList<Calendar>()
        for(i in 0..9) {
            val date = Calendar.getInstance()
            date.add(Calendar.DATE, -i)
            dates.add(date)


        }
        println(dates.toString())
        return dates
    }
}