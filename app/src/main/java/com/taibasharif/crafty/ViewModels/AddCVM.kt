package com.taibasharif.crafty.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taibasharif.crafty.Models.Repositories.Clothe
import com.taibasharif.crafty.Models.Repositories.ClothesRepository
import com.taibasharif.crafty.Models.Repositories.StorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddCVM:ViewModel() {

    val clotheRepo = ClothesRepository()
    val storageRepository = StorageRepository()

    val isSuccessfullySaved = MutableStateFlow<Boolean?>(null)
    val failureMessage = MutableStateFlow<String?>(null)


    fun uploadImageAndSaveClothe(imagePath: String, clothe: Clothe) {
        storageRepository.uploadFile(imagePath, onComplete = { success, result ->
            if (success) {
                clothe.image=result!!
                saveClothe(clothe)
            }
            else failureMessage.value=result
        })
    }
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