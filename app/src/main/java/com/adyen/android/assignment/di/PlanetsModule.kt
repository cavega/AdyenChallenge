package com.adyen.android.assignment.di

import android.app.Application
import android.content.Context
import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.astronomy.ui.list.AstronomyPhotosViewModel
import com.adyen.android.assignment.repository.PlanetsPhotosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanetsModule {

    @Provides
    fun providesAstronomyPhotosViewModel(
        planetsPhotosRepository: PlanetsPhotosRepository
    ): AstronomyPhotosViewModel = AstronomyPhotosViewModel(planetsPhotosRepository)

    @Provides
    @Singleton
    fun providesPlanetsPhotosRepository(
        planetaryService: PlanetaryService
    ): PlanetsPhotosRepository =
        PlanetsPhotosRepository(planetaryService)

    @Provides
    @Singleton
    fun providesPlanetaryService(): PlanetaryService {
        return PlanetaryService.instance
    }

    @Provides
    fun providesPlanetServiceHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    fun providesContext(application: Application): Context = application.applicationContext
}