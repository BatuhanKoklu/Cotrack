package com.batuhankoklu.cotrack.Extensions

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.batuhankoklu.cotrack.Activities.*
import com.batuhankoklu.cotrack.Helpers.Helpers
import com.batuhankoklu.cotrack.Helpers.StaticVariables
import com.batuhankoklu.cotrack.Models.CoinModel
import com.batuhankoklu.cotrack.Models.Price7Days
import com.batuhankoklu.cotrack.Models.UserModel
import com.batuhankoklu.cotrack.Network.ApiClient
import com.batuhankoklu.cotrack.Network.PostService
import com.batuhankoklu.cotrack.R
import kotlinx.android.synthetic.main.activity_coin_detail.*
import kotlinx.android.synthetic.main.activity_master.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


var helper = Helpers()



fun LoginActivity.LoginOnClickListeners(){

    btnSignUpSignIn.setOnClickListener {
        if(btnSignUpSignIn.text == "SIGN IN"){
            LoginUser()
        }else{
            RegisterUser()
        }
    }

    txtSignUpSignIn.setOnClickListener {
        if (txtSignUpSignIn.text == "Don't have account? Sign Up"){
            txtSignUpSignIn.text = "Have account? Sign In"
            btnSignUpSignIn.text = "SIGN UP"
        }
        else{
            txtSignUpSignIn.text = "Don't have account? Sign Up"
            btnSignUpSignIn.text = "SIGN IN"
        }
    }

}

fun LoginActivity.LoginUser() {

    var email = etxtEmail.text.toString()
    var password = etxtPassword.text.toString()

    FirebaseAuth.getInstance().signInWithEmailAndPassword(email , password)
        .addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            helper.getFavoriteCoinsFromFirestore()

            helper.saveUserModel(UserModel(email,password, arrayListOf()),this)

            helper.redirectFromTo(this , MasterActivity::class.java)
            Toast.makeText(this , "Successfull login",Toast.LENGTH_SHORT).show()

        }

        .addOnFailureListener {
            Toast.makeText(this , "Failed to login",Toast.LENGTH_SHORT).show()
        }
}

fun LoginActivity.RegisterUser(){
    var email = etxtEmail.text.toString()
    var password = etxtPassword.text.toString()

    if (email.isEmpty() || password.isEmpty()){
        Toast.makeText(this , "Please enter email and password",Toast.LENGTH_SHORT).show()

    }

    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            helper.saveUserModel(UserModel(email,password, arrayListOf()),this)

            helper.redirectFromTo(this , MasterActivity::class.java)
            Toast.makeText(this , "Successfully created user",Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(this , "Failed to created user",Toast.LENGTH_SHORT).show()
        }


}




fun MasterActivity.MasterOnLoad(){


    bottomNavigationView.setOnNavigationItemSelectedListener(this)

    var pageSelection = StaticVariables.masterPageSelection


    if(pageSelection == 0){
        ChangeFragment(HomeFragment(),"HomeFragment")
        ChangePage(0)
    }
    else if(pageSelection == 1){
        ChangeFragment(FavoritesFragment(),"FavoritesFragment")
        ChangePage(1)
    }
    else{
        ChangeFragment(HomeFragment(),"MapsFragment")
    }


}

fun MasterActivity.ChangeFragment(fragment: Fragment, fragmentTag : String) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.frameLayout, fragment)
    fragmentTransaction.commit()
}

fun MasterActivity.ChangePage(pageSelection : Int){
    var menu : Menu = bottomNavigationView.menu
    var menuItem : MenuItem = menu.getItem(pageSelection)
    menuItem.setChecked(true)

}



fun HomeFragment.HomeOnLoad(){

    GetAllCoins()

    GetUnitsToSpinner()

    etxtSearchCoin.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(coinString: CharSequence?, p1: Int, p2: Int, p3: Int) {

            if (!coinString.isNullOrEmpty()){
                if (coinString?.length!! >= 3){
                    var list = GetSearchedCoins(coinString.toString())
                    helper.prepareRV(rvCoins , list!!, 101 , true , 1 , requireContext())

                }else{
                    rvCoins.adapter = null
                }
            }

        }

    })

}

fun HomeFragment.HomeInitItems(v : View){
    etxtSearchCoin = v.findViewById(R.id.etxtSearchCoin)
    loadingCoins = v.findViewById(R.id.loadingCoins)
    rvCoins = v.findViewById(R.id.rvCoins)
    nestedScrollView = v.findViewById(R.id.scroll_view)
    spinnerUnits = v.findViewById(R.id.spinnerUnits)

}

fun HomeFragment.GetSearchedCoins(name : String) : ArrayList<CoinModel>{

    var emptyList = ArrayList<CoinModel>()
    if (coinList != null){
        for (i in coinList!!){
            if(i.name.contains(name) || i.symbol.contains(name)){
                emptyList!!.add(i)
            }
        }
    }

    return emptyList!!


}

fun HomeFragment.GetUnitsToSpinner(){

    //Listeyi başta boşalt
    spinnerUnits.adapter = null

    var unitList : ArrayList<String>? = arrayListOf("₿","₺","$")

    helper.prepareSpinner(spinnerUnits , unitList!! , requireContext(), false)

    spinnerUnits.setSelection(2)//Default USD

    //Onload da tekrar liste çağırmamıza gerek yok zaten bu işi itemselecten seçmeden yapıyor
    spinnerUnits.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {

        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

            StaticVariables.selectedUnit = position

        }
    }



}

fun HomeFragment.GetAllCoins(){

    loadingCoins.visibility = View.VISIBLE

    postService = ApiClient.getClient().create(PostService::class.java)
    var post = postService.getAllCoins()


    post.enqueue(object : Callback<List<CoinModel>> {
        override fun onFailure(call: Call<List<CoinModel>>?, t: Throwable?) {
            loadingCoins.visibility = View.GONE

            Toast.makeText(requireContext() , t?.message.toString(), Toast.LENGTH_LONG).show()
        }

        override fun onResponse(call: Call<List<CoinModel>>?, response: Response<List<CoinModel>>?) {
            loadingCoins.visibility = View.GONE

            if(response?.isSuccessful!!){
                coinList = (response.body()) as ArrayList<CoinModel>
            }
        }


    })
}





fun CoinDetailActivity.CoinDetailOnLoad(){
    RefreshCoinDetails()
}

fun CoinDetailActivity.CoinDetailOnClickListeners(){

    txtBack.setOnClickListener {
        onBackPressed()
    }

    imgFavoriteCoin.setOnClickListener {
        //if filled make empty
        if(imgFavoriteCoin.drawable.constantState == ContextCompat.getDrawable(this , R.drawable.ic_filled_star)?.constantState){
            imgFavoriteCoin.setImageResource(R.drawable.ic_empty_star)
            //Remove from favorites
            helper.deleteFavoriteCoinFromFirestore(StaticVariables.coinId , this)

        }else{
            imgFavoriteCoin.setImageResource(R.drawable.ic_filled_star)
            //Add to favorites
            helper.saveCoinToFirestore(StaticVariables.coinId , this)
        }
    }


}

fun CoinDetailActivity.RefreshCoinDetails(){
    mainHandler = Handler(Looper.getMainLooper())
    runnable = object : Runnable {
        override fun run() {
            this@RefreshCoinDetails.runOnUiThread {
                GetCoinDetails()
                mainHandler!!.postDelayed(this , 10000)
            }
        }
    }

    mainHandler!!.postDelayed(runnable , 0)
}

fun CoinDetailActivity.GetCoinDetails(){

    //loadingCoins.visibility = View.VISIBLE

    postService = ApiClient.getClient().create(PostService::class.java)
    var post = postService.getCoinDetail(StaticVariables.coinId)


    post.enqueue(object : Callback<CoinModel> {
        override fun onFailure(call: Call<CoinModel>?, t: Throwable?) {
            Toast.makeText(this@GetCoinDetails , t?.message.toString(), Toast.LENGTH_LONG).show()
        }

        override fun onResponse(call: Call<CoinModel>?, response: Response<CoinModel>?) {

            if(response?.isSuccessful!!){
                var coinModel = (response.body()) as CoinModel

                InitCoinDetails(coinModel)

                InitGraph()
            }
        }

    })
}

fun CoinDetailActivity.InitCoinDetails(coinModel : CoinModel){

    if(!doItOnce){
        doItOnce = true
        if(!coinModel.image.large.isNullOrEmpty()){
            helper.urlToImage(coinModel.image.large , imgCoin , this.resources.getDrawable(R.mipmap.ic_project) , this , 0)
            cnsImgRequest.visibility = View.VISIBLE
        }
    }

    var selectedUnit = StaticVariables.selectedUnit

    for (i in StaticVariables.userModel!!.favoriteCoins){
        if (i.coinId == coinModel.id){
            imgFavoriteCoin.setImageResource(R.drawable.ic_filled_star)
            break
        }
    }

    txtCoinName.text = coinModel.name

    var currentPrices = arrayListOf(coinModel.marketData.currentPrice.btc , coinModel.marketData.currentPrice.trlira , coinModel.marketData.currentPrice.usd)
    txtCoinPrice.text = currentPrices[selectedUnit] + " " + StaticVariables.unitTypes[selectedUnit]

    txtCoinRank.text = "Rank : ${coinModel.marketCapRank}"

    var coinVolumes = arrayListOf(coinModel.marketData.marketCap.btc , coinModel.marketData.marketCap.trlira , coinModel.marketData.marketCap.usd)
    txtCoinVolume.text = "Volume : ${coinVolumes[selectedUnit]}" + " " + StaticVariables.unitTypes[selectedUnit]

    txtCoinDetail.text = coinModel.icoData?.description ?: ""

    txtHomepageLink.text = (coinModel.links.homepage?.get(0) ?: "")

    txtRedditLink.text = coinModel.links.subreddit_url ?: ""


    if(!coinModel.marketData.priceChange1H.usd.isNullOrEmpty()){
        txtPercentageChange.visibility = View.VISIBLE
        cnsPriceChange.visibility = View.VISIBLE
        var percentageChanges = arrayListOf(coinModel.marketData.priceChange1H.btc.TwoCharAfterDot() , coinModel.marketData.priceChange1H.trlira.TwoCharAfterDot() , coinModel.marketData.priceChange1H.usd.TwoCharAfterDot())
        txtPercentageChange.text = "% " + percentageChanges[selectedUnit]
    }else{
        txtPercentageChange.visibility = View.GONE
        cnsPriceChange.visibility = View.GONE
    }


    txt1HChange.setOnClickListener {

        var percentageChanges = arrayListOf(coinModel.marketData.priceChange1H.btc.TwoCharAfterDot() , coinModel.marketData.priceChange1H.trlira.TwoCharAfterDot() , coinModel.marketData.priceChange1H.usd.TwoCharAfterDot())
        txtPercentageChange.text = "% " + percentageChanges[selectedUnit]

    }

    txt24HChange.setOnClickListener {

        var percentageChanges = arrayListOf(coinModel.marketData.priceChange24H.btc.TwoCharAfterDot() , coinModel.marketData.priceChange24H.trlira.TwoCharAfterDot() , coinModel.marketData.priceChange24H.usd.TwoCharAfterDot())
        txtPercentageChange.text = "% " + percentageChanges[selectedUnit]
    }

    txt7DChange.setOnClickListener {

        var percentageChanges = arrayListOf(coinModel.marketData.priceChange7D.btc.TwoCharAfterDot() , coinModel.marketData.priceChange7D.trlira.TwoCharAfterDot() , coinModel.marketData.priceChange7D.usd.TwoCharAfterDot())
        txtPercentageChange.text = "% " + percentageChanges[selectedUnit]

    }


}

fun CoinDetailActivity.InitGraph(){

    postServicePrice = ApiClient.getClient().create(PostService::class.java)
    var post = postServicePrice.getCoinWeeklyPrice(StaticVariables.coinId)


    post.enqueue(object : Callback<Price7Days> {
        override fun onFailure(call: Call<Price7Days>?, t: Throwable?) {
            Toast.makeText(this@InitGraph , t?.message.toString(), Toast.LENGTH_LONG).show()

        }

        override fun onResponse(call: Call<Price7Days>?, response: Response<Price7Days>?) {
            if(response?.isSuccessful!!){

                var priceList = (response.body()) as Price7Days

                if(priceList.prices.size > 5){
                    aa_chart_view.visibility = View.VISIBLE

                    val aaChartView = findViewById<AAChartView>(R.id.aa_chart_view)


                    val aaChartModel : AAChartModel = AAChartModel()
                        .chartType(AAChartType.Line)
                        .title("7 Days Price USD")
                        .backgroundColor("#272727")
                        .dataLabelsEnabled(true)
                        .series(arrayOf(
                            AASeriesElement()
                                .name("USD")
                                .data(arrayOf(priceList.prices[0][1].TwoCharAfterDot(),
                                    priceList.prices[1][1].TwoCharAfterDot(),
                                    priceList.prices[2][1].TwoCharAfterDot(),
                                    priceList.prices[3][1].TwoCharAfterDot(),
                                    priceList.prices[4][1].TwoCharAfterDot(),
                                    priceList.prices[5][1].TwoCharAfterDot()))
                        )
                        )

                    //The chart view object calls the instance object of AAChartModel and draws the final graphic
                    aaChartView.aa_drawChartWithChartModel(aaChartModel)


                }else{
                    aa_chart_view.visibility = View.GONE
                }

            }
        }


    })




}







fun FavoritesFragment.FavoritesOnLoad(){


}

fun FavoritesFragment.FavoritesInitItems(v : View){
    rvFavoriteCoins = v.findViewById(R.id.rvFavoriteCoins)

}

fun FavoritesFragment.GetFavoriteCoins(){

    helper.getFavoriteCoinsFromFirestore()

    helper.prepareRV(rvFavoriteCoins , StaticVariables.userModel!!.favoriteCoins!!, 102 , true , 1 , requireContext())
}

