package com.oshamahue.githubtrending.model

import com.squareup.moshi.Json

data class Repo(
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    val description: String,
    @Json(name = "forks_count")
    val forksCount: Int,
    @Json(name = "stargazers_count")
    val starsCount: Int,
    @Json(name = "created_at")
    val createdAt: String,
    val homepage: String?,
    val language: String?,
    val license: License?,
    val owner: Owner,
    @Json(name ="html_url")
    val url: String,
    val score: Double
)

data class Owner(
    @Json(name = "avatar_url")
    val avatarUrl: String,
    val login: String,
    @Json(name ="html_url")
    val url: String
)

data class License(
    val name: String
)

data class GitHubResponse(
    val items: List<Repo>
)