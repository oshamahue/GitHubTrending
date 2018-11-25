package com.oshamahue.githubtrending.api

import com.oshamahue.githubtrending.model.GitHubResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("/search/repositories")
    fun getTrending(
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("q") query: String
    ): Deferred<GitHubResponse>

}
