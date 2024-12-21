package com.taibasharif.crafty.Views.Activities.OrderskiCheezain

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.taibasharif.crafty.Models.Repositories.Clothe
import com.taibasharif.crafty.Models.Repositories.Orders
import com.taibasharif.crafty.ViewModels.PlaceOVM
import com.taibasharif.crafty.databinding.ActivityPlaceOrderBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class PlaceOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlaceOrderBinding;
    lateinit var clothe: Clothe
    lateinit var viewModel: PlaceOVM
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPlaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= PlaceOVM()
        clothe = Gson().fromJson(intent.getStringExtra("data"), Clothe::class.java)
        binding.namepo.text = clothe.title
        binding.pricepo.text = "${clothe.price} Rs."
        binding.sizepo.text=clothe.size
        progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Saving your order...")
        progressDialog.setCancelable(false)
        lifecycleScope.launch {
            viewModel.isSaving.collect{
                if (it==true)
                    progressDialog.show()
                else
                    progressDialog.dismiss()
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect{
                if (it!=null)
                    Toast.makeText(this@PlaceOrderActivity,it,Toast.LENGTH_LONG).show()
            }
        }
        lifecycleScope.launch {
            viewModel.isSaved.collect{
                if (it==true) {
                    Toast.makeText(
                        this@PlaceOrderActivity,
                        "Order saved successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }
        binding.placeOrderButton.setOnClickListener {
            val postalAddress = binding.postalAddress.text.toString()
            val userContact = binding.userContact.text.toString()

            val order= Orders()
            order.item=clothe
            order.status="Order Placed"
            order.postalAddress=postalAddress
            order.userContact=userContact
            order.orderDate= SimpleDateFormat("yyyy-MM-dd HH:mm a").format(System.currentTimeMillis())
            val user=viewModel.getCurrentUser()
            order.userEmail=user?.email!!
            order.userName=user?.displayName!!
            order.userId=user?.uid!!
            viewModel.saveOrder(order)
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}