package com.adyen.android.assignment.repository

import com.adyen.android.assignment.api.model.AstronomyPicture
import kotlinx.coroutines.flow.Flow

interface PlanetsRepository {

    suspend fun getPhotos(): Flow<Result<List<AstronomyPicture>>>
}