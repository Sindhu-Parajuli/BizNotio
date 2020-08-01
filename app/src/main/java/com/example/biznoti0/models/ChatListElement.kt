package com.example.biznoti0.models

data class ChatListElement(

    var message_preview: String,

    var body: String,

    var image: String,

    var username: String // Author of blog post


) {

    override fun toString(): String {
        return "BlogPost(title='$message_preview', image='$image', username='$username')"
    }


}