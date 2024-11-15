package com.adyen.android.assignment.astronomy.ui.list

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

/**
 * This view model is responsible for getting all the data from the repository and
 * producing the various UI states that allow the consumer (i.e. view) to render
 * and update the UI accordingly.
 *
 * The ViewModel exposes an API that allows the view to request operations to be
 * performed based on the various users taken by the user.
 */
@HiltViewModel
class AstronomyPhotosViewModel @Inject constructor(
    private val planetsRepository: PlanetsPhotosRepository
) : ViewModel() {

    private var _uiState = MutableLiveData<AstronomyPhotosListUiState>()
    val uiState = _uiState

    private var sortType: SortType = SortType.Date

    fun sortPhotos(sortType: SortType) {
        this.sortType = sortType
        // We don't need to refresh the data when re-ordering the photos
        loadCollection(sortType = sortType, forceRefresh = false)
    }

    fun loadCollection(sortType: SortType = SortType.Date, forceRefresh: Boolean = true) {
        viewModelScope.launch {
            // TODO: With a caching strategy (i.e. Room database) implemented by the
            //  repository, we can keep can keep track of the last time the data was refreshed
            //  to further minimize the number of API calls. At this moment we are
            //  just using a in-memory cache.
            planetsRepository.getPhotos(forceRefresh = forceRefresh)
                .onStart {
                    _uiState.value = AstronomyPhotosListUiState.Loading
                }
                .catch {
                    // TODO: We should log errors to Firebase or similar service
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
            SortType.Date -> {
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
        object Date : SortType()
        object Title : SortType()
    }
}