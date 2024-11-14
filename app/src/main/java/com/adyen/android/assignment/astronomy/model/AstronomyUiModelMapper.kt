package com.adyen.android.assignment.astronomy.model

import com.adyen.android.assignment.api.model.AstronomyPicture

fun AstronomyPicture.toAstronomyItemUiModel(): AstronomyItemUiModel {
    return AstronomyItemUiModel(
        title = title,
        date = date,
        url = url
    )
}