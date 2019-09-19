package com.moustafa.countrypicker.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */
@JsonClass(generateAdapter = true)
data class Country(
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "capital")
    val capital: String? = null,
    @field:Json(name = "flag")
    val flagUrl: String? = null,
    @field:Json(name = "timezones")
    val timeZones: List<String>? = null,
    @field:Json(name = "altSpellings")
    val altSpellings: List<String>? = null,
    @field:Json(name = "latlng")
    val mapLatLng: List<Double>? = null

) {
    val description
        get() = if (altSpellings?.size ?: 0 > 2) altSpellings?.get(1) else null

    fun timeZonesFormatted(maxTimeZones: Int) = timeZones?.take(maxTimeZones)?.joinToString(",")

}