package com.example.biznoti0

import android.content.Intent
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_rating.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.fragment_profile.*

class Rating : AppCompatActivity() {
    private lateinit var firebaseuser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)
        ratingBar.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {

                Toast.makeText(this@Rating, "Given rating is: $p1", Toast.LENGTH_SHORT).show()
               // number.text = "$p1"
                val intent = Intent(this@Rating, MainActivity::class.java)
                startActivity(intent)

            }
        })

        fun savechanges() {

            val userreference = FirebaseDatabase.getInstance().reference.child("usersID")

            val currUserHashMap = HashMap<String, Any>()


            //currUserHashMap["Rating"] = EnterProfession.text.toString().toLowerCase()
            currUserHashMap["Rating"] = number.text


            userreference.child(firebaseuser.uid).updateChildren(currUserHashMap)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Toast.makeText(this, "Information successfully updated.", Toast.LENGTH_LONG).show();
                        startActivity(Intent(this@Rating, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show();
                    }


                }


        }
    }
}



