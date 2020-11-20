package com.batuhankoklu.cotrack.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.batuhankoklu.cotrack.Extensions.LoginOnClickListeners
import com.batuhankoklu.cotrack.Extensions.helper
import com.batuhankoklu.cotrack.Models.UserModel
import com.batuhankoklu.cotrack.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        this.LoginOnClickListeners()


    }


}