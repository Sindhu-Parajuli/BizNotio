package com.example.biznoti0

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class CreatePost : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_post)

        sign.setOnClickListener {
            startActivity(Intent(this, CreatePost::class.java))
        }
        Post.setOnClickListener {
            CreatePost();
        }
    }

    override fun onStart() {
        super.onStart()

        if(FirebaseAuth.getInstance().currentUser!=null)
        {
            val intent = Intent(this@CreatePost,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun CreatePost() {
        val mfirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val title = ProposalName.text.toString()
        val description = ProposalDescription.text.toString()
        val minCase = MinimumCase.text.toString()
        val link = Link.text.toString()

        if (title.isEmpty()) {
            Toast.makeText(this, "Must add a title.", Toast.LENGTH_LONG)
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Must add description.", Toast.LENGTH_LONG)
        } else if (minCase.isEmpty()) {
            Toast.makeText(this, "Must add minimum business case.", Toast.LENGTH_LONG)
        } //else {
//
//            val progressDialog = ProgressDialog(this@SignInActivity)
//            progressDialog.setTitle("Logging In")
//            progressDialog.setMessage("Logging-in In progress")
//            progressDialog.setCanceledOnTouchOutside(false)
//            progressDialog.show()
//        }
    }
}

