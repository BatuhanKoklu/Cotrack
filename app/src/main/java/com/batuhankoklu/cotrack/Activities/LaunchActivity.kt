package com.batuhankoklu.cotrack.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.batuhankoklu.cotrack.Helpers.Helpers
import com.batuhankoklu.cotrack.Helpers.StaticVariables
import com.batuhankoklu.cotrack.R

class LaunchActivity : AppCompatActivity() {

    var helper = Helpers()

    var mDelayHandler: Handler? = null
    val SPLASH_DELAY: Long = 3000 //3 seconds

    val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

           if (IsHaveUser(this)){
               helper.redirectAndClearAllPreviousActivities(this, MasterActivity::class.java)
           }else{
               helper.redirectAndClearAllPreviousActivities(this, LoginActivity::class.java)
           }


        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lauch)

        if(StaticVariables.isEnteredBefore == 0){
            //Initialize the Handler
            mDelayHandler = Handler()

            //Navigate with delay
            mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

            StaticVariables.isEnteredBefore = StaticVariables.isEnteredBefore + 1

        }else{
            /*StaticVariables.masterPageSelection = 1
            com.appollob.teknik10.Extensions.helper.redirectFromTo(this, MasterActivity::class.java)*/
            finish()
        }

    }

    fun IsHaveUser(context : Context) : Boolean{
        var userModel = helper.readUserModelFromSharedPrefs(context,"SharedPrefUserModel" , Context.MODE_PRIVATE)
        if(userModel != null){
            //If userModel exist, put to StaticVariables

            StaticVariables.userModel = userModel

            helper.getFavoriteCoinsFromFirestore()

            return true

        }else{
            return false
        }

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}