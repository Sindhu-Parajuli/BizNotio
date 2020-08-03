package com.example.biznoti0.ViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.biznoti0.Model.User


class ChatViewModel : ViewModel() {
    val selectedUser: MutableLiveData<User> = MutableLiveData<User>()

    fun select(user : User) {
        selectedUser.value = user
    }

}