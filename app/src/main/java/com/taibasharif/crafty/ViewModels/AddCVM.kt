package com.taibasharif.crafty.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taibasharif.crafty.Models.Repositories.Clothe
import com.taibasharif.crafty.Models.Repositories.ClothesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddCVM:ViewModel() {
    val clotheRepo = ClothesRepository()
    val isSuccessfullySaved = MutableStateFlow<Boolean?>(null)
    val failureMessage = MutableStateFlow<String?>(null)
    fun saveClothe(clothe: Clothe) {
        viewModelScope.launch {
            val result = clotheRepo.saveClothe(clothe)
            if (result.isSuccess) {
                isSuccessfullySaved.value = true
            } else {
                failureMessage.value = result.exceptionOrNull()?.message
            }
        }
    }

}