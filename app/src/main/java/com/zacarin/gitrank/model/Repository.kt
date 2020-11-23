package com.zacarin.gitrank.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Class that represents a [Repository].
 */
data class Repository(
    @SerializedName("incomplete_results")
    @Expose
    val incompleteResults: Boolean,
    @SerializedName("items")
    @Expose
    val items: List<Item>,
    @SerializedName("total_count")
    @Expose
    val totalCount: Int
)
