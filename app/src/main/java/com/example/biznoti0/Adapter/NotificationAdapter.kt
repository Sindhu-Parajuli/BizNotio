package com.example.biznoti0.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.biznoti0.Model.Notification
import com.example.biznoti0.Model.ProfileUser
import com.example.biznoti0.Model.Proposal
import com.example.biznoti0.R

class NotificationAdapter(val context_adapter: Context,
                          private var notifcationlist:List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>()
{
    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        var userName: TextView
        var text: TextView

        init {

            userName = itemView.findViewById(R.id.username)
            text = itemView.findViewById(R.id.says)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}