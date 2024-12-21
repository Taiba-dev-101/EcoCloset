package com.taibasharif.crafty.Views.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taibasharif.crafty.ViewModels.AuthVM
import kotlinx.coroutines.launch
import com.taibasharif.crafty.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    lateinit var binding: ActivitySignUpBinding
    lateinit var viewModel: AuthVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= AuthVM()
        viewModel.checkUser()

        progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Sabr karain... time lagta hai aesi cheezon main:)")
        progressDialog.setCancelable(false)

        lifecycleScope.launch {
            viewModel.failureMessage.collect{
                progressDialog.dismiss()
                if (it!=null){
                    Toast.makeText(this@SignUpActivity,it,Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.currentUser.collect{
                if (it!=null){
                    progressDialog.dismiss()
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finish()
                }
            }
        }

        binding.Already.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.SignUp.setOnClickListener {
            val name=binding.Name.editText?.text.toString()
            val email=binding.EmailS.editText?.text.toString()
            val Password=binding.Pass.editText?.text.toString()
            val confirm=binding.Conp.editText?.text.toString()

            if(name.toString().trim().isEmpty()){
                Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!email.contains("@")){
                Toast.makeText(this,"Invalid Email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(Password.length<6){
                Toast.makeText(this,"Password must be atleast 6 characters",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!Password.equals(confirm)){
                Toast.makeText(this,"Confirm password does not match with password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressDialog.show()

            viewModel.signUp(email,Password,name)

        }


    }
}