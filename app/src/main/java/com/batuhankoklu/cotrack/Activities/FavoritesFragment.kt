package com.batuhankoklu.cotrack.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.batuhankoklu.cotrack.Extensions.FavoritesInitItems
import com.batuhankoklu.cotrack.Extensions.FavoritesOnLoad
import com.batuhankoklu.cotrack.Extensions.GetFavoriteCoins
import com.batuhankoklu.cotrack.Extensions.helper
import com.batuhankoklu.cotrack.Helpers.StaticVariables
import com.batuhankoklu.cotrack.R
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    lateinit var rvFavoriteCoins : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var v = inflater.inflate(R.layout.fragment_favorites, container, false)

        this.FavoritesOnLoad()

        this.FavoritesInitItems(v)

        return v
    }

    override fun onResume() {
        super.onResume()
        this.GetFavoriteCoins()
    }


}