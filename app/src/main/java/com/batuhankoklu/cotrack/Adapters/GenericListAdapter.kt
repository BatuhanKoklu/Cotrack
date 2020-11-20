package com.batuhankoklu.cotrack.Adapters

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.batuhankoklu.cotrack.Activities.CoinDetailActivity
import com.batuhankoklu.cotrack.Extensions.helper
import com.batuhankoklu.cotrack.Helpers.StaticVariables
import com.batuhankoklu.cotrack.Models.CoinModel
import com.batuhankoklu.cotrack.Models.FavoriteCoinModel
import com.batuhankoklu.cotrack.R

class GenericListAdapter<T>(genericList : List<T>, cellType : Int, context: Context?) : RecyclerView.Adapter<GenericListAdapter.GenericListViewHolder>() {

    var cellType = cellType
    var genericList = genericList
    var context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericListViewHolder {

        //Geçici
        var view = View(context)

        if(cellType in 100..200){

            if(cellType == 101){
                //Home Coin Cell
                view = LayoutInflater.from(parent.context).inflate(R.layout.cell_coin_simple, parent, false)
            }else if(cellType == 102){
                //Favorite Coin Cell
                view = LayoutInflater.from(parent.context).inflate(R.layout.cell_coin_simple, parent, false)
            }


        }

        return GenericListViewHolder(view, cellType , context!!)

    }

    override fun getItemCount(): Int {
        return genericList.size
    }

    override fun onBindViewHolder(holder: GenericListViewHolder, position: Int) {

        if(cellType in 100..200){

            if(cellType == 101){
                //All Coins
                genericList as List<CoinModel>
                var item = genericList.get(position)
                item as CoinModel

                holder.bindItems(item)

                holder.cnsCoinCellSimple.setOnClickListener {
                    //Detail Page

                    StaticVariables.coinId = item.id
                    helper.redirectFromTo(context!! , CoinDetailActivity::class.java)

                }

            }
            else if(cellType == 102){
                //All Coins
                genericList as List<FavoriteCoinModel>
                var item = genericList.get(position)
                item as FavoriteCoinModel

                holder.bindItems(item)

                holder.cnsCoinCellSimple.setOnClickListener {
                    //Detail Page

                    StaticVariables.coinId = item.coinId
                    helper.redirectFromTo(context!! , CoinDetailActivity::class.java)

                }

            }


        }


    }

    class GenericListViewHolder(itemView : View, cellType : Int, context: Context) : RecyclerView.ViewHolder(itemView) {

        var context = context
        var cellType = cellType

        lateinit  var cnsCoinCell : ConstraintLayout
        lateinit  var cnsCoinCellSimple : ConstraintLayout


        fun bindItems(item: Any?){

            if(cellType in 100..200){

                if(cellType == 101){

                    var txtCoinName : TextView = itemView.findViewById(R.id.txtCoinName)
                    cnsCoinCellSimple = itemView.findViewById(R.id.cnsCoinSimpleCell) // Detail Button


                    item as CoinModel

                    txtCoinName.text = item.name

                }
                else if(cellType == 102){
                    var txtCoinName : TextView = itemView.findViewById(R.id.txtCoinName)
                    cnsCoinCellSimple = itemView.findViewById(R.id.cnsCoinSimpleCell) // Detail Button


                    item as FavoriteCoinModel

                    txtCoinName.text = item.coinId
                }

                /*else if(cellType == 102){
                    //Coin
                    var txtCoinName : TextView = itemView.findViewById(R.id.txtCoinName)
                    var txtCoinVolume : TextView =  itemView.findViewById(R.id.txtCoinVolume)
                    var txtCoinPrice : TextView =  itemView.findViewById(R.id.txtCoinPrice)
                    var txtPriceChange : TextView =  itemView.findViewById(R.id.txtPriceChange)
                    var cnsCoinStatus : ConstraintLayout =  itemView.findViewById(R.id.cnsCoinStatus)
                    cnsCoinCell = itemView.findViewById(R.id.cnsCoinCell) // Detail Button


                    item as CoinModel

                    txtCoinName.text = item.symbol.toUpperCase()

                    if (StaticVariables.currencyType ==  "btc"){

                        txtCoinPrice.text = item.marketData.currentPrice.btc
                        var oneDayPriceChange = item.marketData.priceChange24H.btc
                        txtPriceChange.text = oneDayPriceChange
                        txtCoinVolume.text = "Hacim : " + item.marketData.marketCap.btc

                        if(oneDayPriceChange.startsWith("-")){
                            cnsCoinStatus.setBackgroundColor(ContextCompat.getColor(context , R.color.colorRed))
                        }else{
                            cnsCoinStatus.setBackgroundColor(ContextCompat.getColor(context , R.color.colorGreen))
                        }
                    }else if(StaticVariables.currencyType ==  "try"){

                        txtCoinPrice.text = item.marketData.currentPrice.trlira
                        var oneDayPriceChange = item.marketData.priceChange24H.trlira
                        txtPriceChange.text = oneDayPriceChange
                        txtCoinVolume.text = "Hacim : " + item.marketData.marketCap.trlira

                        if(oneDayPriceChange.startsWith("-")){
                            cnsCoinStatus.setBackgroundColor(ContextCompat.getColor(context , R.color.colorRed))
                        }else{
                            cnsCoinStatus.setBackgroundColor(ContextCompat.getColor(context , R.color.colorGreen))
                        }
                    }else if(StaticVariables.currencyType ==  "usd"){

                        txtCoinPrice.text = item.marketData.currentPrice.usd
                        var oneDayPriceChange = item.marketData.priceChange24H.usd
                        txtPriceChange.text = oneDayPriceChange
                        txtCoinVolume.text = "Hacim : " + item.marketData.marketCap.usd

                        if(oneDayPriceChange.startsWith("-")){
                            cnsCoinStatus.setBackgroundColor(ContextCompat.getColor(context , R.color.colorRed))
                        }else{
                            cnsCoinStatus.setBackgroundColor(ContextCompat.getColor(context , R.color.colorGreen))
                        }
                    }


                }*/


            }

        }
    }

}