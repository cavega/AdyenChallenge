package com.adyen.android.assignment.astronomy.model

import java.time.LocalDate

data class AstronomyItemUiModel(
    val title: String,
    val date: LocalDate,
    val url: String?,
)