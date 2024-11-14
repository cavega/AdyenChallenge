package com.adyen.android.assignment.repository

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class PlanetsPhotosRepository @Inject constructor(private val planetaryService: PlanetaryService) :
    PlanetsRepository {
    override suspend fun getItems(): Flow<Response<List<AstronomyPicture>>> {
        return flow {
            emit(planetaryService.getPictures())
        }
    }
}