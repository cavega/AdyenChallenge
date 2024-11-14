package com.adyen.android.assignment.repository

/**
 * Sealed class that allows us to abstract away the Response returned
 * by the Retrofit API calls, such that repositories can do most of the
 * data formatting and validation and return a clean success or error state
 * to the consumer (i.e. ViewModel)
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}