package com.taibasharif.crafty.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.taibasharif.crafty.Models.Repositories.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainVM: ViewModel() {


    val authRepo= AuthRepo()
    val currentUser = MutableStateFlow<FirebaseUser?>(null)
    init {
        currentUser.value=authRepo.getCurrentUser()

     }



    fun logout(){
        viewModelScope.launch {
            authRepo.logout()
            currentUser.value=null
        }
    }

}