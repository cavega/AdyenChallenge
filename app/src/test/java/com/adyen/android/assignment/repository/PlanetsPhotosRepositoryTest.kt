package com.adyen.android.assignment.repository

import com.google.common.truth.Truth.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * This class tests the various scenarios in which usage of the repository
 * results in different types of success of failure responses.
 */
class PlanetsPhotosRepositoryTest {

    private val planetaryService = PlanetServiceTestMock()
    private val repository = PlanetsPhotosRepository(planetaryService)

    @Test
    fun `successful response returns list of photos`() = runTest {
        planetaryService.setMockData(PlanetServiceTestMock.successJson)
        val result = repository.getPhotos(forceRefresh = true).first()
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val dataResponse = (result as Result.Success).data
        assertThat(dataResponse).hasSize(3)
    }

    @Test
    fun `successful response returns non-null non-empty url photo list`() = runTest {
        planetaryService.setMockData(PlanetServiceTestMock.successBadDataJson)
        val result = repository.getPhotos(forceRefresh = true).first()
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val dataResponse = (result as Result.Success).data
        assertThat(dataResponse).hasSize(1)
    }

    @Test
    fun `successful response returns empty list of photos`() = runTest {
        planetaryService.setMockData("[]")
        val result = repository.getPhotos(forceRefresh = true).first()
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val dataResponse = (result as Result.Success).data
        assertThat(dataResponse).isEmpty()
    }

    @Test
    fun `error response returns error message`() = runTest {
        planetaryService.setMockData(null)
        val result = repository.getPhotos(forceRefresh = true).first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }
}