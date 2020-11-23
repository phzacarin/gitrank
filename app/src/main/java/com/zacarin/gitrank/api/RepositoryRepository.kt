package com.zacarin.gitrank.api

import com.zacarin.gitrank.model.Repository

/**
 * Data repository.
 */
class RepositoryRepository (private val endpoint: ApiAccess) {

    suspend fun getRepositories(page: Int? = 1): RepositoryResult<Repository> {
        return try {
            val result = createEndpoint().getRepositories(page = page)
            RepositoryResult.Success(result)
        } catch (ex: Exception) {
            RepositoryResult.Error(ex)
        }
    }

    private fun createEndpoint() = endpoint.retrofit.create(RepositoryEndpoint::class.java)
}
