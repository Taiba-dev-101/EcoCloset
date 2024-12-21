package com.taibasharif.crafty.Views.Activities.ClotheSave

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taibasharif.crafty.ViewModels.AddCVM
import com.taibasharif.crafty.Models.Repositories.Clothe
import com.taibasharif.crafty.databinding.ActivityAddClotheBinding
import kotlinx.coroutines.launch

class AddClotheActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddClotheBinding;
    lateinit var viewModel: AddCVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddClotheBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // viewModel = AddCVM()
        lifecycleScope.launch {
            viewModel.isSuccessfullySaved.collect {
                it?.let {
                    if (it == true) {
                        Toast.makeText(
                            this@AddClotheActivity,
                            "Successfully saved",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@AddClotheActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.AddCB.setOnClickListener {
            val title = binding.nameinput.text.toString().trim()
            val description = binding.descinput.text.toString().trim()
            val priceText = binding.priceinput.text.toString().trim()
            val size= binding.sizeinput.text.toString()
            // Validate the input fields
            if (title.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val price = priceText.toIntOrNull()
            if (price == null) {
                Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Create a Handcraft object with the entered data
            val clothe = Clothe()
            clothe.title = title
            clothe.price = price
            clothe.description = description
            clothe.size= size
            viewModel.saveClothe(clothe)
            // Save the Handcraft object (this would be a database operation, Firestore, etc.)
            // For now, just display the success message
            Toast.makeText(this, "Handcraft Added Successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}