package com.batuhankoklu.cotrack.Models

import com.google.gson.annotations.SerializedName

data class CoinModel(
     @SerializedName("id") val id : String,
     @SerializedName("symbol") val symbol : String,
     @SerializedName("name") val name : String,
     @SerializedName("links") val links : Links,
     @SerializedName("image") val image : Image,
     @SerializedName("ico_data") val icoData : IcoData?,
     @SerializedName("market_cap_rank") val marketCapRank : Int,
     @SerializedName("market_data") val marketData : MarketData

)

data class Links(
     @SerializedName("homepage") val homepage : ArrayList<String>?,
     @SerializedName("subreddit_url") val subreddit_url : String?
)

data class Image(
     @SerializedName("thumb") val thumb : String,
     @SerializedName("small") val small : String,
     @SerializedName("large") val large : String
)

data class IcoData(
     @SerializedName("description") val description : String = ""
)

data class MarketData(
     @SerializedName("current_price") val currentPrice : CurrentPrice,
     @SerializedName("market_cap") val marketCap : MarketCap,
     @SerializedName("price_change_percentage_1h_in_currency") val priceChange1H : PriceChange1H,
     @SerializedName("price_change_percentage_24h_in_currency") val priceChange24H : PriceChange24H,
     @SerializedName("price_change_percentage_7d_in_currency") val priceChange7D : PriceChange7D
)

data class CurrentPrice(
     @SerializedName("btc") val btc : String,
     @SerializedName("try") val trlira : String,
     @SerializedName("usd") val usd : String
)

data class MarketCap(
     @SerializedName("btc") val btc : String,
     @SerializedName("try") val trlira : String,
     @SerializedName("usd") val usd : String
)

data class PriceChange1H(
     @SerializedName("btc") val btc : String,
     @SerializedName("try") val trlira : String,
     @SerializedName("usd") val usd : String
)
data class PriceChange24H(
     @SerializedName("btc") val btc : String,
     @SerializedName("try") val trlira : String,
     @SerializedName("usd") val usd : String
)
data class PriceChange7D(
     @SerializedName("btc") val btc : String,
     @SerializedName("try") val trlira : String,
     @SerializedName("usd") val usd : String
)

data class Price7Days(
     @SerializedName("prices") val prices : ArrayList<ArrayList<Double?>>
)

data class UserModel(
     var email : String,
     var password : String,
     var favoriteCoins : ArrayList<FavoriteCoinModel>
)

data class FavoriteCoinModel(
     var documentId : String,
     var coinId : String
)

