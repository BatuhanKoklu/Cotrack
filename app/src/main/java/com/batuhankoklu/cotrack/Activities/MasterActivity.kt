package com.batuhankoklu.cotrack.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.batuhankoklu.cotrack.Extensions.ChangeFragment
import com.batuhankoklu.cotrack.Extensions.MasterOnLoad
import com.batuhankoklu.cotrack.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MasterActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        this.MasterOnLoad()

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnHome -> {
                ChangeFragment(HomeFragment(), "MapsFragment")

                return true
            }
            R.id.btnFavorites -> {
                ChangeFragment(FavoritesFragment(), "FavoritesFragment")

                return true
            }

        }
        return false
    }


    override fun onBackPressed() {


    }

}