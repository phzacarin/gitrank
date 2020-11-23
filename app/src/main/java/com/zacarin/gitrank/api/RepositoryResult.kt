package com.zacarin.gitrank.api

/**
 * Class to represent a [Success] or [Error] when calling the API.
 */
sealed class RepositoryResult<out T : Any> {
    /**
     * Represents a successful API call.
     */
    class Success<out T : Any>(val data: T) : RepositoryResult<T>()

    /**
     * Represents an unsuccessful API call.
     */
    class Error(val exception: Throwable) : RepositoryResult<Nothing>()
}