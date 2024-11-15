package com.adyen.android.assignment.astronomy.model

import java.time.LocalDate

/**
 * This data class abstracts away the response objects returned by the repository
 * in order to decouple the UI layer from the network layer. The ViewModels uses
 * a mapper function to convert the network model from the repository into this
 * model that is used by the View/UI. This provides a clear separation of concerns
 * and improves maintainability of the code.
 */
data class AstronomyItemUiModel(
    val title: String,
    val date: LocalDate,
    val url: String?,
)