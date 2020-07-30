package com.example.biznoti0

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        deletebutton.setOnClickListener {
            val user =  FirebaseAuth.getInstance().currentUser!!

            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, SignInActivity::class.java))
                        Toast.makeText(this, "This Account has been deleted.", Toast.LENGTH_LONG).show()
                    }
                }

        }



    }
}
