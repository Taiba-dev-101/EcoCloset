package com.taibasharif.crafty.Views.Activities.ClotheSave

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taibasharif.crafty.databinding.ActivityClotheDetailBinding
import com.google.gson.Gson
import com.taibasharif.crafty.Models.Repositories.Clothe
import com.taibasharif.crafty.Views.Activities.OrderskiCheezain.PlaceOrderActivity


class ClotheDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityClotheDetailBinding;
    lateinit var clothe: Clothe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityClotheDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clothe = Gson().fromJson(intent.getStringExtra("data"), Clothe::class.java)

        binding.title.text = clothe.title
        binding.size.text=clothe.size
        binding.description.text = clothe.description
        binding.price.text = "${clothe.price} Rs."
        binding.orderButton.setOnClickListener {
            startActivity(Intent(this, PlaceOrderActivity::class.java).putExtra("data", Gson().toJson(clothe)))
            finish()
        }
    }
}