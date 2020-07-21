package com.example.biznotio

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    companion object {
        private const val RC = 1
        private const val TAG = "SignInActivity"
    }

    private var googleSignInClient : GoogleSignInClient? = null

    private var fireBaseAuth: FirebaseAuth? = null
    private var firebaseAuth : FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        fireBaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this@SignInActivity,gso)
        sign_in_button.setOnClickListener {
            signIn()

        }

    }

    private fun signIn()
    {
        val SignInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(SignInIntent,RC)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode== RC )
        {
            if(resultCode== Activity.RESULT_OK)
            {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try{
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account!!)
                }catch(e:ApiException){
                   // Log.e(TAG,msg:"Google failed $e")

                }
            }
        }

    }
    private fun firebaseAuthWithGoogle(account:GoogleSignInAccount)
    {
       val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        fireBaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this@SignInActivity){task ->
                if(task.isSuccessful)
                {
                    startActivity(Intent(this@SignInActivity,MainActivity::class.java))
                    finish()
                }
            }
    }
    }


