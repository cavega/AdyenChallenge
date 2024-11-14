package com.adyen.android.assignment.repository

import com.adyen.android.assignment.api.model.AstronomyPicture
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PlanetsRepository {

    suspend fun getItems(): Flow<Response<List<AstronomyPicture>>>
}