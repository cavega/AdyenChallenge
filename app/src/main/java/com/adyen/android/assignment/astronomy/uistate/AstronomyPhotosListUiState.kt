package com.adyen.android.assignment.astronomy.uistate

import com.adyen.android.assignment.astronomy.model.AstronomyItemUiModel

sealed class AstronomyPhotosListUiState {

    data object Loading : AstronomyPhotosListUiState()

    data class DataLoaded(val items: List<AstronomyItemUiModel>) : AstronomyPhotosListUiState()

    data class Error(val message: String) : AstronomyPhotosListUiState()
}