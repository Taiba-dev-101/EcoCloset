package com.taibasharif.crafty.Models.Repositories
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ClothesRepository {
    val ClothesCollection = FirebaseFirestore.getInstance().collection("clothes")
    suspend fun saveClothe(clothe: Clothe): Result<Boolean> {
        try {
            val document = ClothesCollection.document()
            clothe.id = document.id
            document.set(clothe).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
    fun getClothes() =
        ClothesCollection.snapshots().map { it.toObjects(Clothe::class.java) }
}
