package com.zacarin.gitrank.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Class that represents an [Item].
 */
data class Item(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("forks_count")
    @Expose
    val forksCount: Int,
    @SerializedName("stargazers_count")
    @Expose
    val stargazersCount: Int,
    @SerializedName("owner")
    @Expose
    val owner: Owner
)
