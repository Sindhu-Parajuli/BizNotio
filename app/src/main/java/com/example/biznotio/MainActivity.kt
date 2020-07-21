package com.example.biznotio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener{

    companion object{
        const val TAG= "MainActivity"
        const val ANONYMOUS = "anonymus"
    }

    private var userName: String? = null
    private var userPhotoUrl: String? = null
    private var fireBaseAuth: FirebaseAuth? = null
    private var fireBaseUser: FirebaseUser? = null
    private var googleApiClient: GoogleApiClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API).build();


    }

    override fun onConnectionFailed(p0: ConnectionResult) {
       // Log.d(TAG,msg: "onConnectionFailed $p0 ")
        //Toast.makeText(context:this,"Google Play Services error",Toast.LENGTH_SHORT).show()
         userName= ANONYMOUS
        fireBaseAuth = FirebaseAuth.getInstance()
        fireBaseUser=fireBaseAuth!!.currentUser

        if(fireBaseUser==null)
        {
            val intent = Intent(this@MainActivity,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            userName=fireBaseUser!!.displayName
            if(fireBaseUser!!.photoUrl!=null)
            {
                userPhotoUrl = fireBaseUser!!.photoUrl!!.toString()
            }
        }
    }
}
