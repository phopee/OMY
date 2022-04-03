package com.example.omy.entities

import com.squareup.moshi.Json

data class Symbols( @Json(name = "symbols")
                    val rates: List<Pair<String?,String?>>)
