package com.taibasharif.crafty.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taibasharif.crafty.Models.Repositories.AuthRepo
import com.taibasharif.crafty.Models.Repositories.Orders
import com.taibasharif.crafty.Models.Repositories.OrdersRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PlaceOVM: ViewModel(){
    val orderRepo= OrdersRepo()
    val authRepo= AuthRepo()
    val isSaving= MutableStateFlow<Boolean>(false)
    val isSaved= MutableStateFlow<Boolean?>(null)
    val failureMessage= MutableStateFlow<String?>(null)
    fun getCurrentUser() = authRepo.getCurrentUser()
    fun saveOrder(order: Orders){
        viewModelScope.launch {
            val result=orderRepo.saveOrder(order)
            isSaving.value=false
            if (result.isSuccess){
                isSaved.value=true
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }



}