package com.taibasharif.crafty.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taibasharif.crafty.Models.Repositories.Orders
import com.taibasharif.crafty.Models.Repositories.OrdersRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class OrderFragVM: ViewModel(){
    val orderRepo = OrdersRepo()
    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<Orders>?>(null)
    init {
        readClothes()
    }
    fun readClothes() {
        viewModelScope.launch {
            orderRepo.getOrders().catch {
                failureMessage.value = it.message
            }
                .collect {
                    data.value = it
                }
        }
    }
}