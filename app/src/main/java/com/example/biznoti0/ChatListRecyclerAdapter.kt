package com.example.biznoti0

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_chat_list_element.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.biznoti0.models.ChatListElement
import kotlin.collections.ArrayList


class ChatListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var items: List<ChatListElement> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChatListElementViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_chat_list_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is ChatListElementViewHolder -> {
                holder.bind(items.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: List<ChatListElement>){
        items = blogList
    }

    class ChatListElementViewHolder
    constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val profile_image = itemView.chat_list_element_image_circle
        val message_preview = itemView.chat_list_element_message_preview
        val user_name = itemView.chat_list_element_user_name

        fun bind(chatListElement: ChatListElement){

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(chatListElement.image)
                .into(profile_image)
            message_preview.setText(chatListElement.message_preview)
            user_name.setText(chatListElement.username)

        }

    }

}