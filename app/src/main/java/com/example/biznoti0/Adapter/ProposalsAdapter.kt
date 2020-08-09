package com.example.biznoti0.Adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.biznoti0.Model.ProfileUser
import com.example.biznoti0.Model.Proposal
import com.example.biznoti0.Model.User
import com.example.biznoti0.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.layout_chat_list_element.view.*
import kotlinx.android.synthetic.main.single_post.view.*

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.biznoti0.Adapter.ProfileAdapter
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class ProposalsAdapter(val context_adapter: Context, val proposals: List<Proposal>) :
        RecyclerView.Adapter<ProposalsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context_adapter).inflate(R.layout.single_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = proposals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindIt(proposals[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindIt(proposals: Proposal) {

            val ownerId = proposals.owner
            val getUserFName = FirebaseDatabase.getInstance().reference.child("usersID").child(ownerId).child("FName")
            val getUserLName = FirebaseDatabase.getInstance().reference.child("usersID").child(ownerId).child("LName")
            getUserFName.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userFName = dataSnapshot.value.toString()
                    getUserLName.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userLName = snapshot.value.toString()
                            itemView.tVName.text = "$userFName $userLName"
                        }
                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
            itemView.tVProposalTitle.text = proposals.proposalName
            itemView.tVProposalType.text = proposals.proposalType
            itemView.tVMinCase.text = proposals.minimumCase
            itemView.tVDescription.text = proposals.proposalDescription
            Glide.with(context_adapter).load(proposals.link).into(itemView.imageViewPostImg)
            itemView.tVRelativeTime.text = DateUtils.getRelativeTimeSpanString(proposals.timeCreated)
        }
    }
}

