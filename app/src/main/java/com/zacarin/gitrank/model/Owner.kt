package com.zacarin.gitrank.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Class that represents an [Owner].
 */
data class Owner(
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String,
    @SerializedName("login")
    @Expose
    val login: String
)
