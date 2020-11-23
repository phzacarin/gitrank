package com.zacarin.gitrank.api

import com.zacarin.gitrank.model.Item
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class responsible to manage [Item] information API access.
 */
class ApiAccess {

    /**
     * Creates instance of [ApiAccess].
     */
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}
