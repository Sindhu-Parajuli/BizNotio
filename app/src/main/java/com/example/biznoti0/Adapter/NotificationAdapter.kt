package com.example.biznoti0.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.biznoti0.Model.Notification
import com.example.biznoti0.Model.ProfileUser
import com.example.biznoti0.Model.Proposal
import com.example.biznoti0.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_sign_up.view.*

class NotificationAdapter(val context_adapter: Context,
                          private var notificationlist:List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context_adapter).inflate(R.layout.notification,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notificationlist[position]
        if (notification.text.equals("added you as a connection"))
        {
            holder.text.text = "added you as a connection"
        }

    }






    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        var userName: TextView
        var text: TextView

        init {

            userName = itemView.findViewById(R.id.username)
            text = itemView.findViewById(R.id.says)
        }
    }
 private fun user(userName:TextView,IDforpfile:String)
 {
     val ref = FirebaseDatabase.getInstance().getReference().child("Notifications").child(IDforpfile)
      ref.addValueEventListener(object:ValueEventListener
      {
          override fun onCancelled(error: DatabaseError) {

          }

          override fun onDataChange(snapshot: DataSnapshot) {
              if(snapshot.exists())
              {
                  val newuser = snapshot.getValue<ProfileUser>(ProfileUser::class.java)

                  userName.text = newuser!!.getFNAME()

              }
          }

      }

      )


 }













}