package com.batuhankoklu.cotrack.Helpers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batuhankoklu.cotrack.Adapters.GenericListAdapter
import com.batuhankoklu.cotrack.Adapters.SpinnerListAdapter
import com.batuhankoklu.cotrack.Models.CoinModel
import com.batuhankoklu.cotrack.Models.FavoriteCoinModel
import com.batuhankoklu.cotrack.Models.UserModel
import com.batuhankoklu.cotrack.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class Helpers {

    fun redirectFromTo(context : Context, to : Class<*>){
        var intent = Intent(context,to)
        context.startActivity(intent)

    }

    fun redirectAndClearAllPreviousActivities(context: Context, to : Class<*>){
        var intent = Intent(context,to)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun <T> prepareSpinner(spinner : Spinner, list : List<T>, context: Context, setSelection : Boolean = true){

        /*val popup = Spinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true


        // Get private mPopup member variable and try cast to ListPopupWindow
        //val popupWindow : android.widget.Spinner = popup.get(spinnerCity) as android.widget.Spinner

        // Set popupWindow height to 500px
        //popupWindow.layoutParams.height = 500*/

        spinner?.adapter = SpinnerListAdapter(context , list)

        if (setSelection){
            spinner!!.setSelection(0,true)
        }

    }

    fun <T> prepareRV(recyclerView : RecyclerView, list : List<T>, cellType : Int, isVertical : Boolean, spanCount : Int, context : Context ){

        if(isVertical){
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false)
        }
        else{
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false)
        }


        recyclerView.adapter = GenericListAdapter(list,cellType,context)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = GridLayoutManager(context , spanCount)
    }

    fun saveUserModelToSharedPref(context: Context, userModel: UserModel){
        var sharedPreferences : SharedPreferences = context.getSharedPreferences("SharedPrefUserModel",Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()
        var gson = Gson()
        var userJsonModel = gson.toJson(userModel)
        editor.putString("userModel",userJsonModel)
        editor.commit()
    }

    fun readUserModelFromSharedPrefs(context : Context,sharedPrefName :String , sharedPrefKey : Int ) : UserModel? {
        var sharedPreferences : SharedPreferences? = context.getSharedPreferences(sharedPrefName,sharedPrefKey)
        if(sharedPreferences != null){
            var editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            var gson = Gson()
            var userJsonModel : String? = sharedPreferences?.getString("userModel","")
            var userModel  = gson.fromJson(userJsonModel, UserModel::class.java)
            return userModel
        }

        return null

    }

    fun saveUserModel(userModel: UserModel, context: Context){
        saveUserModelToSharedPref(context , userModel)
        StaticVariables.userModel = userModel
    }

    fun saveCoinToFirestore(coinId : String , context: Context){
        var db = Firebase.firestore

        var coin = hashMapOf("coinId" to coinId)

        db.collection("favorite_coins")
            .add(coin)
            .addOnSuccessListener {
                Toast.makeText(context , "Coin added successfully to favorites" , Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context , "Coin failed to add favorites" , Toast.LENGTH_SHORT).show()

            }
    }

    fun getFavoriteCoinsFromFirestore(){

        var db = Firebase.firestore

        var favCoinList = ArrayList<FavoriteCoinModel>()

        db.collection("favorite_coins")
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    favCoinList.add(FavoriteCoinModel(document.id ,document.data.get("coinId") as String))
                }
                StaticVariables.userModel?.favoriteCoins = favCoinList
            }

    }

    fun deleteFavoriteCoinFromFirestore(coinId: String , context: Context){

        getFavoriteCoinsFromFirestore()

        var db = Firebase.firestore

        var favCoin : FavoriteCoinModel? = findFavCoinFromAllCoins(coinId , StaticVariables.userModel!!.favoriteCoins)

        if (favCoin != null){
            db.collection("favorite_coins").document(favCoin.documentId)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context , "Coin deleted successfully from favorites" , Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context , "Coin failed to delete from favorites" , Toast.LENGTH_SHORT).show()

                }
        }else{
            Toast.makeText(context , "Coin failed to delete from favorites" , Toast.LENGTH_SHORT).show()
        }

    }

    fun findFavCoinFromAllCoins(coinId : String , coinList : ArrayList<FavoriteCoinModel>) : FavoriteCoinModel?{

        var coin : FavoriteCoinModel? = null

        for (i in coinList){
            if (i.coinId == coinId){
                coin = i
            }
        }
        return coin

    }

    fun urlToImage(url: String?, imageView: ImageView, defaulImage : Drawable, context: Context?, type : Int = -1) {

        try {
            Picasso.get()
                .load(url)
                .placeholder(defaulImage)
                .error(ContextCompat.getDrawable(context!!, R.mipmap.ic_project)!!)
                .into(imageView)
        }
        catch (ex : java.lang.Exception){
            if(type == 0){
                imageView.layoutParams.height = intToDp(context!!, 32)
                imageView.layoutParams.width  = intToDp(context!!, 32)
                imageView.requestLayout()
            }

            imageView.setImageDrawable(defaulImage)

        }

    }

    fun intToDp(context: Context , value : Int) : Int{
        return (value * context!!.resources.displayMetrics.density).toInt()
    }

}