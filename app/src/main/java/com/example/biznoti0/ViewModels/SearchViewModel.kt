package com.example.biznoti0.ViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.biznoti0.Model.ProfileUser
import com.example.biznoti0.Model.User


class SearchViewModel : ViewModel() {
    val selectedUser: MutableLiveData<ProfileUser> = MutableLiveData<ProfileUser>()

    // when we do selectedUser.value we will get the User object that was passed in
    fun select(user : ProfileUser) {
        selectedUser.value = user
    }

}