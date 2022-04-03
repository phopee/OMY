package com.example.omy.entities
import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class ExchangeRate(@SerializedName("success")val success :Boolean, @SerializedName("timestamp") val timestamp: Long,
                        @SerializedName("base" ) val base :String,
                        @SerializedName("date") val date :String,
                        @SerializedName("rates") var rates: Map<String, Double>
)