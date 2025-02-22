package com.taibasharif.crafty.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.taibasharif.crafty.Models.Repositories.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthVM:ViewModel(){
    val authRepo=AuthRepo()
    val currentUser= MutableStateFlow<FirebaseUser?>(null)
    val failureMessage= MutableStateFlow<String?>(null)
    val resetResponse= MutableStateFlow<Boolean?>(null)

    fun login(email:String,password:String){
        viewModelScope.launch{
            val result=authRepo.login(email,password)
            if (result.isSuccess){
                currentUser.value=result.getOrThrow()
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }

        }

    }

    fun signUp(email:String,password:String,name:String){
        viewModelScope.launch {
            val result=authRepo.signup(email,password,name)
            if (result.isSuccess){
                currentUser.value=result.getOrThrow()
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }


    fun resetPassword(email:String){
        viewModelScope.launch {
            val result=authRepo.resetPassword(email)
            if (result.isSuccess){
                resetResponse.value=result.getOrThrow()
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }

fun checkUser(){
    currentUser.value=authRepo.getCurrentUser()
}
}