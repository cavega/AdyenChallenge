package com.adyen.android.assignment.astronomy.ui.list

import com.adyen.android.assignment.repository.PlanetServiceTestMock
import com.adyen.android.assignment.repository.PlanetsPhotosRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * This class is meant to test all UI States emitted by the ViewModel
 * as the result of the view's calls to the ViewModel methods\
 *
 * TODO: Implement tests for all UI States
 */
class AstronomyPhotosViewModelTest {

    private val planetaryService = PlanetServiceTestMock()
    private val repository = PlanetsPhotosRepository(planetaryService)
    private val viewModel = AstronomyPhotosViewModel(repository)


    @Test
    fun `success response results in Success state with data`() = runTest {
        // Verify Loading UI State
        // Verify DataLoaded UI State
    }

    @Test
    fun `success response results in Error state with data`() = runTest {
        // Verify Loading UI State
        // Verify Error UI State
        // Verify refresh/retry
    }

    @Test
    fun `sortPhotos with SortType Title returns list ordered by ascending title`() = runTest {
    }

    @Test
    fun `sortPhotos with SortType Title returns list ordered by descending date`() = runTest {
    }
}