package com.example.biznoti0.Adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.biznoti0.Model.Proposal
import com.example.biznoti0.Model.User
import com.example.biznoti0.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.layout_chat_list_element.view.*
import kotlinx.android.synthetic.main.single_post.view.*

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
            val ref = FirebaseDatabase.getInstance().getReference("/usersID/${proposals.owner}")
            ref.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue(User::class.java)
                    itemView.tVName.text = "${user?.FName} ${user?.LName} "

                    /*Glide.with(viewHolder.itemView).load(user?.profileImageUrl).into(viewHolder.itemView.chat_list_element_image_circle)
                    viewHolder.itemView.chat_list_element_image.alpha = 0f*/
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
            //itemView.tVName.text = proposals.owner?.FName + " " + proposals.owner?.LName
            itemView.tVProposalTitle.text = proposals.proposalName
            itemView.tVProposalType.text = proposals.proposalType
            itemView.tVMinCase.text = proposals.minimumCase
            itemView.tVDescription.text = proposals.proposalDescription
            Glide.with(context_adapter).load(proposals.link).into(itemView.imageViewPostImg)
            itemView.tVRelativeTime.text = DateUtils.getRelativeTimeSpanString(proposals.timeCreated)
        }
    }
}