package com.adyen.android.assignment.api

import kotlinx.coroutines.test.runTest
import org.junit.Test


class PlanetaryServiceTest {

    /**
     * Integration test -
     * ensures the [generated key](https://api.nasa.gov/) returns results from the api
     */
    @Test
    fun testResponseCode() = runTest {
        val response = PlanetaryService.instance.getPictures()
        assert(response.isSuccessful)
    }
}
