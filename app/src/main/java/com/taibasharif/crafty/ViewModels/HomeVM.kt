package com.taibasharif.crafty.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taibasharif.crafty.Models.Repositories.Clothe
import com.taibasharif.crafty.Models.Repositories.ClothesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeVM: ViewModel() {
    val clotherepo = ClothesRepository()
    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<Clothe>?>(null)
    init {
        readClothes()
    }
    fun readClothes() {
        viewModelScope.launch {
            clotherepo.getClothes().catch {
                failureMessage.value = it.message
            }
                .collect {
                    data.value = it
                }
        }
    }
}