package com.batuhankoklu.cotrack.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.batuhankoklu.cotrack.Extensions.CoinDetailOnClickListeners
import com.batuhankoklu.cotrack.Extensions.CoinDetailOnLoad
import com.batuhankoklu.cotrack.Extensions.TwoCharAfterDot
import com.batuhankoklu.cotrack.Extensions.helper
import com.batuhankoklu.cotrack.Helpers.StaticVariables
import com.batuhankoklu.cotrack.Models.CoinModel
import com.batuhankoklu.cotrack.Models.Price7Days
import com.batuhankoklu.cotrack.Network.ApiClient
import com.batuhankoklu.cotrack.Network.PostService
import com.batuhankoklu.cotrack.R
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import kotlinx.android.synthetic.main.activity_coin_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class CoinDetailActivity : AppCompatActivity() {

    lateinit var postService : PostService
    lateinit var postServicePrice : PostService

    var mainHandler : Handler? =  null
    var runnable : Runnable? = null

    var doItOnce : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        this.CoinDetailOnLoad()

        this.CoinDetailOnClickListeners()

    }




    override fun onPause() {
        super.onPause()
        if(mainHandler != null){
            mainHandler!!.removeCallbacks(runnable)
        }

    }
}