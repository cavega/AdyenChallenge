package com.adyen.android.assignment.repository

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlanetsPhotosRepository @Inject constructor(private val planetaryService: PlanetaryService) :
    PlanetsRepository {
    override suspend fun getPhotos(): Flow<Result<List<AstronomyPicture>>> {

        val response = planetaryService.getPictures()

        val result = if (response.isSuccessful) {
            Result.Success(response.body()?.filter<AstronomyPicture> { !it.url.isNullOrBlank() }
                ?: emptyList<AstronomyPicture>())
        } else {
            val message = response.errorBody()?.string() ?: "No releases found"
            Result.Error(message)
        }

        return flow {
            emit(result)
        }
    }
}