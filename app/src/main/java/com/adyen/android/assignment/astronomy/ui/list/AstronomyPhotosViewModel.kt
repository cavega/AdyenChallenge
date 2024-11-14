package com.adyen.android.assignment.astronomy.ui.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.astronomy.model.toAstronomyItemUiModel
import com.adyen.android.assignment.astronomy.ui.list.AstronomyPhotosViewModel.SortType
import com.adyen.android.assignment.astronomy.uistate.AstronomyPhotosListUiState
import com.adyen.android.assignment.repository.PlanetsPhotosRepository
import com.adyen.android.assignment.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AstronomyPhotosViewModel @Inject constructor(
    private val planetsRepository: PlanetsPhotosRepository
) : ViewModel() {

    private var _uiState = MutableLiveData<AstronomyPhotosListUiState>()
    val uiState = _uiState

    private var sortType: SortType = SortType.Latest

    init {
        loadCollection()
    }

    fun sortPhotos(sortType: SortType) {
        this.sortType = sortType
        loadCollection(sortType)
    }

    fun loadCollection(sortType: SortType = SortType.Latest) {
        viewModelScope.launch {
            planetsRepository.getPhotos()
                .onStart {
                    _uiState.value = AstronomyPhotosListUiState.Loading
                }
                .catch {
                    // TODO: We should log errors to Firebase or similar service
                    Log.e(
                        AstronomyPhotosViewModel::class.simpleName,
                        "Error loading photos",
                        it
                    )
                    _uiState.value = AstronomyPhotosListUiState.Error("No releases found")
                }
                .collect { response ->
                    when (response) {
                        is Result.Success -> {
                            val sortedList =
                                sortPhotosByType(response.data, sortType)
                                    .map { it.toAstronomyItemUiModel() }

                            _uiState.value = AstronomyPhotosListUiState.DataLoaded(sortedList)
                        }

                        is Result.Error -> {
                            _uiState.value = AstronomyPhotosListUiState.Error(response.message)
                        }
                    }
                }
        }
    }

    private fun sortPhotosByType(
        items: List<AstronomyPicture>,
        sortType: SortType
    ): List<AstronomyPicture> {
        return when (sortType) {
            SortType.Latest -> {
                items.sortedByDescending { it.date }
            }

            SortType.Title -> {
                items.sortedBy { it.title }
            }

            else -> {
                items.sortedByDescending { it.date }
            }
        }
    }

    sealed class SortType {
        object Latest : SortType()
        object Title : SortType()
    }
}