package com.example.biznoti0

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpIn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))

        }

        register.setOnClickListener {
            Registration()
        }

    }

    private fun Registration()
    {
        val mfirebaseAuth: FirebaseAuth
        mfirebaseAuth = FirebaseAuth.getInstance()
        val Names = SignUpName.text.toString()
        val emails = SignUpName.text.toString()
        val passwords = SignUpName.text.toString()


        if (Names.isEmpty())
        {
            Toast.makeText(this,"Name is must",Toast.LENGTH_LONG)
        }

        else if (emails.isEmpty())
        {
            Toast.makeText(this,"Email is must",Toast.LENGTH_LONG)
        }
        else if (passwords.isEmpty())
        {
            Toast.makeText(this,"Password is must",Toast.LENGTH_LONG)
        }
        else
        {
            mfirebaseAuth.createUserWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener{ task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        store(Names,emails)
                    }


                }
        }


    }

   private fun store(Names:String,emails:String)
    {

    }

}
