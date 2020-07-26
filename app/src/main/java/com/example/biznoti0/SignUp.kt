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
    private lateinit var mFireAuth: FirebaseAuth
    private lateinit var userreference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        mFireAuth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            Registration()
        }

    }

    private fun Registration() {
        val Fnames = SignUpFName.text.toString()
        val Lnames = SignUpLName.text.toString()
        val Mnames = SignUpMName.text.toString()
        val emails = SignUpEmail.text.toString()
        val passwords = SignUpPassword.text.toString()

        if (Fnames.isEmpty()) {
            Toast.makeText(this, "First Name is required", Toast.LENGTH_LONG).show()
        } else if (Lnames.isEmpty()) {
            Toast.makeText(this, "Last Name is required", Toast.LENGTH_LONG).show()
        } else if (emails.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show()
        } else if (passwords.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show()
        } else {
            val progressDialog = ProgressDialog(this@SignUp)
            progressDialog.setTitle("SignUp")
            progressDialog.setMessage("Sign up Process in Progress.....")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            mFireAuth.createUserWithEmailAndPassword(emails, passwords).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (Mnames == "") {
                            store(Fnames, "N/A", Lnames, emails, progressDialog)
                        }
                        else {
                            store(Fnames, Mnames, Lnames, emails, progressDialog)
                        }
                    } else {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_LONG).show();
                        finish()
                    }
                }
        }


    }

    private fun store(Fnames: String, Mnames: String, Lnames: String, emails: String, progressDialog: ProgressDialog) {

        var curruserId = mFireAuth.currentUser!!.uid
        userreference = FirebaseDatabase.getInstance().reference.child("usersID").child(curruserId)

        val currUserHashMap = HashMap<String, Any>()

        currUserHashMap["usersID"] = curruserId
        currUserHashMap["FName"] = Fnames
        currUserHashMap["MName"] = Mnames
        currUserHashMap["LName"] = Lnames
        currUserHashMap["Email"] = emails
        currUserHashMap["Image"] = "ts://bitnoti0.appspot.com/user Info/profile.png"
        userreference.updateChildren(currUserHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sign up Success", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@SignUp, MainActivity::class.java)
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


