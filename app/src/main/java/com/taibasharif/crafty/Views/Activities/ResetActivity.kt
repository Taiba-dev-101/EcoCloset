package com.taibasharif.crafty.Views.Activities

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.taibasharif.crafty.ViewModels.AuthVM
import com.taibasharif.crafty.databinding.ActivityResetBinding
import kotlinx.coroutines.launch

class ResetActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    lateinit var binding: ActivityResetBinding
    lateinit var viewModel: AuthVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= AuthVM()
        progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Please wait while...")
        progressDialog.setCancelable(false)
        lifecycleScope.launch {
            viewModel.failureMessage.collect{
                progressDialog.dismiss()
                if (it!=null){
                    Toast.makeText(this@ResetActivity,it,Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.resetResponse.collect{
                progressDialog.dismiss()
                if (it!=null){
                    val builder= MaterialAlertDialogBuilder(this@ResetActivity)
                    builder.setMessage("We have sent you a password reset email, check your inbox and click the link to reset your password")
                    builder.setCancelable(false)
                    builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                        finish()
                    })
                    builder.show()
                }
            }
        }
        binding.back.setOnClickListener {
            finish()
        }
        binding.submit.setOnClickListener {
            val email=binding.email.editText?.text.toString()
            if(!email.contains("@")){
                Toast.makeText(this,"Invalid Email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progressDialog.show()
            viewModel.resetPassword(email)
        }
    }
}