package com.example.biznoti0

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))

        }

        register.setOnClickListener {
            Registration()
        }

    }

    private fun Registration() {
        val mfirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val names = SignUpName.text.toString()
        val emails = SignUpEmail.text.toString()
        val passwords = SignUpPassword.text.toString()


        if (names.isEmpty()) {
            Toast.makeText(this, "Name is must", Toast.LENGTH_LONG)
        } else if (emails.isEmpty()) {
            Toast.makeText(this, "Email is must", Toast.LENGTH_LONG)
        } else if (passwords.isEmpty()) {
            Toast.makeText(this, "Password is must", Toast.LENGTH_LONG)
        } else {

            val progressDialog = ProgressDialog(this@SignUp)
            progressDialog.setTitle("SignUp")
            progressDialog.setMessage("Sign up Process in Progress.....")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()



            mfirebaseAuth.createUserWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    } else {
                        store(names, emails, progressDialog)
                    }


                }
        }


    }

    private fun store(names: String, emails: String, progressDialog: ProgressDialog) {

        val curruserId = FirebaseAuth.getInstance().currentUser!!.uid

        val userreference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(curruserId)

        userreference.child("Name").setValue(names)
        userreference.child("email").setValue(emails)
        userreference.child("Image").setValue("gs://bitnoti0.appspot.com/user Info/profile.png")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sign up Success", Toast.LENGTH_SHORT).show();

                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                else
                {
                    Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
                }


            }

    }


}


