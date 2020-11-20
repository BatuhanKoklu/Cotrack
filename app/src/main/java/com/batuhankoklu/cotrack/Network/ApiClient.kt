package com.batuhankoklu.cotrack.Network

import com.batuhankoklu.cotrack.Helpers.StaticVariables
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null)
            retrofit =
                Retrofit.Builder().baseUrl(StaticVariables.baseUrl).addConverterFactory(
                    GsonConverterFactory.create()).build()

        return retrofit as Retrofit
    }
}