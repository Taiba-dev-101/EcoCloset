package com.taibasharif.crafty.RecyclerView_ki_cheezain

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.taibasharif.crafty.Models.Repositories.AuthRepo
import com.taibasharif.crafty.Models.Repositories.Orders
import com.taibasharif.crafty.ViewModels.OrderKiDetsVM
import com.taibasharif.crafty.databinding.ActivityOrderkiDetsBinding

class OrderkiDetsActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderkiDetsBinding
    lateinit var order: Orders
    lateinit var viewModel: OrderKiDetsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderkiDetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
viewModel=OrderKiDetsVM()
        order= Gson().fromJson(intent.getStringExtra("data"),Orders::class.java)
        binding.orderId.text = order.id
        binding.orderDate.text = order.orderDate
        binding.item.text = order.item?.title ?: "N/A"
        binding.sizedets.text = order.item?.size
        binding.postalAddress.text = order.postalAddress
        binding.userName.text = order.userName
        binding.userEmail.text = order.userEmail
        binding.userContact.text = order.userContact
        binding.status.text = order.status
        val user: FirebaseUser = AuthRepo().getCurrentUser()!!
        var isAdmin=false
        if (user.email.equals("taibasharif101@gmail.com"))
            isAdmin=true
        if (!order.status.equals("Order Placed")||!isAdmin)
            binding.confirmOrder.visibility= View.GONE
        if (!order.status.equals("Order Confirmed")||!isAdmin)
            binding.deliverOrder.visibility= View.GONE
        if (!order.status.equals("Delivered")||isAdmin)
            binding.confirmOrderReceived.visibility= View.GONE
        binding.confirmOrder.setOnClickListener {
            order.status="Order Confirmed"
            viewModel.updateOrder(order)
        }
        binding.deliverOrder.setOnClickListener {
            order.status="Delivered"
            viewModel.updateOrder(order)
        }
        binding.confirmOrderReceived.setOnClickListener {
            order.status="Order Received"
            viewModel.updateOrder(order)
        }


    }
}