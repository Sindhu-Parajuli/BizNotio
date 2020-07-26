package com.example.biznoti0

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sign.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))

        }


        login.setOnClickListener {
            SignIn();
        }
    }

    override fun onStart() {
        super.onStart()

        if(FirebaseAuth.getInstance().currentUser!=null)
        {
            val intent = Intent(this@SignInActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun SignIn() {
        val mfirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val emails = email.text.toString()
        val passwords = Password.text.toString()

        if (emails.isEmpty()) {
            Toast.makeText(this, "Email is must", Toast.LENGTH_LONG).show()
        } else if (passwords.isEmpty()) {
            Toast.makeText(this, "Password is must", Toast.LENGTH_LONG).show()
        } else {

            val progressDialog = ProgressDialog(this@SignInActivity)
            progressDialog.setTitle("Logging In")
            progressDialog.setMessage("Logging-in In progress")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            mfirebaseAuth.signInWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss()
                    } else {
                        val intent = Intent(this@SignInActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
        }
    }

}
