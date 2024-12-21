package com.taibasharif.crafty.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taibasharif.crafty.Models.Repositories.OrdersRepo
import kotlinx.coroutines.flow.MutableStateFlow
import com.taibasharif.crafty.Models.Repositories.Orders
import kotlinx.coroutines.launch


class OrderKiDetsVM: ViewModel() {
    val orderRepository=OrdersRepo()

    val isUpdated= MutableStateFlow<Boolean?>(null)
    val failureMessage= MutableStateFlow<String?>(null)

    public fun updateOrder(order:Orders){
        viewModelScope.launch {
            val result=orderRepository.updateOrder(order)
            if (result.isSuccess)
                isUpdated.value=true
            else
                failureMessage.value=result.exceptionOrNull()?.message
        }
    }
}