package com.batuhankoklu.cotrack.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.batuhankoklu.cotrack.Extensions.HomeInitItems
import com.batuhankoklu.cotrack.Extensions.HomeOnLoad
import com.batuhankoklu.cotrack.Models.CoinModel
import com.batuhankoklu.cotrack.Network.ApiClient
import com.batuhankoklu.cotrack.Network.PostService
import com.batuhankoklu.cotrack.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var postService : PostService
    var coinModel : CoinModel? = null
    var coinList : ArrayList<CoinModel>? = null
    var page = 1
    var isLoading = false
    var limit = 50

    lateinit var etxtSearchCoin: EditText
    lateinit var loadingCoins: ProgressBar
    lateinit var rvCoins: RecyclerView
    lateinit var nestedScrollView: NestedScrollView
    lateinit var spinnerUnits : Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var v = inflater.inflate(R.layout.fragment_home, container, false)

        this.HomeInitItems(v)

        this.HomeOnLoad()




        return v
    }


}