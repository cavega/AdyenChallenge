package com.adyen.android.assignment.api.model

import com.squareup.moshi.Json
import java.time.LocalDate

/**
 * Notes:
 *
 * 1) endpoint/service is returning null for "url" parameter. This results in JsonDataException
 * 2) The url was changed to optional (?) to avoid crashes.
 */
data class AstronomyPicture(
    @Json(name = "service_version") val serviceVersion: String,
    val title: String,
    val explanation: String,
    val date: LocalDate,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "hdurl") val hdUrl: String?,
    val url: String?,
)