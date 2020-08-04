package com.example.biznoti0

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.biznoti0.Model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_appointment_select_user.*
import kotlinx.android.synthetic.main.layout_chat_new_message_user_row.view.*

class AppointmentSelectUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_select_user)
        val adapter = GroupAdapter<GroupieViewHolder>()

        recyclerView_selectUser.adapter = adapter
        fetchUsers()
    }


    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/usersID")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()

                p0.children.forEach {
                    Log.d("AppointmentSelectUser", it.toString())
                    val user = it.getValue<User>()
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }
                /*adapter.setOnItemClickListener {item, view ->
                    //val userItem = item as UserItem
                    //model.select(userItem.user)
                    startActivity(Intent(this@AppointmentSelectUser, AppointmentSetup::class.java))
                }*/
                recyclerView_selectUser.adapter = adapter

            }


            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: User): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.username_textview_new_message.text = user.FName
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)

        // So the tutorial was using picasso but I found better loading times with glide
        Glide.with(viewHolder.itemView)
            .applyDefaultRequestOptions(requestOptions)
            .load(user.profileImageUrl)
            .into(viewHolder.itemView.imageview_new_message)

    }

    override fun getLayout(): Int {
        return R.layout.layout_chat_new_message_user_row
    }
}