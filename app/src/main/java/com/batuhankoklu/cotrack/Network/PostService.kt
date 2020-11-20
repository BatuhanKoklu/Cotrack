package com.batuhankoklu.cotrack.Network

import com.batuhankoklu.cotrack.Models.CoinModel
import com.batuhankoklu.cotrack.Models.Price7Days
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PostService {

    @GET("coins/{id}")
    fun getCoinDetail(@Path("id") coinName: String): Call<CoinModel>

    @GET("coins/")
    fun getTopCoins(): Call<List<CoinModel>>

    @GET("coins/list")
    fun getAllCoins(): Call<List<CoinModel>>

    @GET("coins/{id}/market_chart?vs_currency=usd&days=6&interval=daily")
    fun getCoinWeeklyPrice(@Path("id") coinName: String): Call<Price7Days>


}