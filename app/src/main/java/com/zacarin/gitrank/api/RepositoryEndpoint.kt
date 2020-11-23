package com.zacarin.gitrank.api

import com.zacarin.gitrank.model.Item
import com.zacarin.gitrank.model.Repository
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [Item] endpoint for server request.
 */
interface RepositoryEndpoint {

    /**
     * Gets all available repositories ordered by star count.
     */
    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String = "language:kotlin",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int? = 1
    ): Repository
}
