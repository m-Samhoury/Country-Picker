package com.moustafa.countrypicker.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */
@JsonClass(generateAdapter = true)
@Parcelize
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
    @field:Json(name = "population")
    val population: Long? = null,
    @field:Json(name = "latlng")
    val mapLatLng: List<Double>? = null,
    @field:Json(name = "regionalBlocs")
    val regionalBlocs: List<RegionalBloc>? = null

) : Parcelable {
    val description
        get() = if (altSpellings?.size ?: 0 > 2) altSpellings?.get(1) else null

    fun timeZonesFormatted(maxTimeZones: Int? = null) =
        timeZones?.joinToString(
            limit = maxTimeZones ?: Integer.MAX_VALUE,
            separator = ",",
            postfix = " "
        )
}

@JsonClass(generateAdapter = true)
@Parcelize
data class RegionalBloc(
    @field:Json(name = "acronym")
    val acronym: String,
    @field:Json(name = "name")
    val name: String
) : Parcelable