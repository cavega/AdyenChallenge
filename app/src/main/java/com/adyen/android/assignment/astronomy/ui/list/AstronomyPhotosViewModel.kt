package com.adyen.android.assignment.astronomy.ui.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.astronomy.model.AstronomyItemUiModel
import com.adyen.android.assignment.astronomy.model.toAstronomyItemUiModel
import com.adyen.android.assignment.astronomy.uistate.AstronomyPhotosListUiState
import com.adyen.android.assignment.repository.PlanetsPhotosRepository
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

    init {
        loadCollection()
    }

    private fun loadCollection() {
        viewModelScope.launch {
            planetsRepository.getItems()
                .onStart {
                    _uiState.value = AstronomyPhotosListUiState.Loading
                }
                .catch {
                    Log.e(
                        AstronomyPhotosViewModel::class.simpleName,
                        "Error loading photos",
                        it
                    )
                    _uiState.value = AstronomyPhotosListUiState.Error("No releases found")
                }
                .collect { response ->
                    if (response.isSuccessful) {
                        // We are filtering photos with missing url since the
                        // photos are the central feature of the app
                        val items = response.body()?.filter { !it.url.isNullOrBlank() }
                            ?.map { it.toAstronomyItemUiModel() }
                            ?: emptyList<AstronomyItemUiModel>()
                        _uiState.value = AstronomyPhotosListUiState.DataLoaded(items)
                    } else {
                        _uiState.value = AstronomyPhotosListUiState.Error("No releases found")
                    }
                }
        }
    }
}