package com.taibasharif.crafty.Models.Repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class OrdersRepo {
    val orderCollection = FirebaseFirestore.getInstance().collection("orders")
    suspend fun saveOrder(order: Orders): Result<Boolean> {
        try {
            val document = orderCollection.document()
            order.id = document.id
            document.set(order).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }


    suspend fun updateOrder(order: Orders): Result<Boolean> {
        try {
            val document = orderCollection.document(order.id!!)
            document.set(order).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
    fun getOrders() =
        orderCollection.snapshots().map { it.toObjects(Orders::class.java) }
}