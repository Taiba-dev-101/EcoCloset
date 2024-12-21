package com.taibasharif.crafty.Views.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taibasharif.crafty.ViewModels.AuthVM
import com.taibasharif.crafty.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: AuthVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= AuthVM()
        val progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Please wait while we check your credentials...")
        progressDialog.setCancelable(false)
        viewModel.checkUser()


        lifecycleScope.launch {
            viewModel.failureMessage.collect{
                progressDialog.dismiss()
                if (it!=null){
                    Toast.makeText(this@LoginActivity,it,Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.currentUser.collect{
                if (it!=null){
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    progressDialog.dismiss()
                    finish()


                }
            }
        }

        binding.CN.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.Reset.setOnClickListener {
            startActivity(Intent(this, ResetActivity::class.java))
        }








        binding.login.setOnClickListener {
            val email=binding.email.editText?.text.toString()
            val password=binding.password.editText?.text.toString()

            if(!email.contains("@")){
                Toast.makeText(this,"Ghalat Email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.length<6){
                Toast.makeText(this,"Password must be atleast 6 characters",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(email,password)


            progressDialog.show()



        }

        }
}


