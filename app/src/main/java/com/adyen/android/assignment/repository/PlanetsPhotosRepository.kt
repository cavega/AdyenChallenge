package com.adyen.android.assignment.repository

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * The repository acts as an abstraction layer between the View/ViewModel domain and the
 * network layer. All network calls from the API are executed by this class. The network response
 * is validated (i.e. success vs failure) and any data sanitation is performend before the
 * results are sent to the ViewModel.
 *
 * TODO: With a database, this class can implement a more robust caching strategy that
 *  prevents data loss and avoid unnecessary network calls. For the purposes of this assignment,
 *  we are just using the `photosCache` as an in-memory cache that avoids calling the API
 *  if we already have some data.
 */
class PlanetsPhotosRepository @Inject constructor(private val planetaryService: PlanetaryService) :
    PlanetsRepository {

    private var photosCache: List<AstronomyPicture>? = null

    override suspend fun getPhotos(forceRefresh: Boolean): Flow<Result<List<AstronomyPicture>>> {

        if (!photosCache.isNullOrEmpty() && !forceRefresh) {
            return flow {
                emit(Result.Success<List<AstronomyPicture>>(photosCache ?: emptyList()))
            }
        }

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