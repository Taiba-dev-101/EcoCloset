package com.taibasharif.crafty.Views.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.taibasharif.crafty.ChatActivity
import com.taibasharif.crafty.Models.Repositories.AuthRepo
import com.taibasharif.crafty.Models.Repositories.Clothe
import com.taibasharif.crafty.RecyclerView_ki_cheezain.ClotheAdapter
import com.taibasharif.crafty.ViewModels.HomeVM
import com.taibasharif.crafty.Views.Activities.ClotheSave.AddClotheActivity
import com.taibasharif.crafty.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class homeFragment : Fragment() {
    lateinit var adapter: ClotheAdapter
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeVM
    val items=ArrayList<Clothe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HomeFragment", "HomeFragment created")

        adapter= ClotheAdapter(items)
        binding.rv1.adapter= adapter
        binding.rv1.layoutManager= LinearLayoutManager(context)
        viewModel= HomeVM()
        lifecycleScope.launch {
           viewModel.failureMessage.collect {
               it?.let {
                  Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
           }
       }
        lifecycleScope.launch {
            viewModel.data.collect {
                it?.let {
                    //Toast.makeText(context, it.size.toString(), Toast.LENGTH_SHORT).show()
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }

   }
        val user: FirebaseUser = AuthRepo().getCurrentUser()!!
        var isAdmin=false

        if (user.email.equals("taibasharif101@gmail.com"))
            isAdmin=true
        if (!isAdmin)
            binding.floatingActionButton.visibility= View.GONE

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(context, AddClotheActivity::class.java))
        }

        binding.button.setOnClickListener {
            // Check if the context is available
            Toast.makeText(requireContext(), "Start Chat button clicked", Toast.LENGTH_SHORT).show()

            activity?.let {
                val intent = Intent(requireContext(), ChatActivity::class.java)
                startActivity(intent)
            }
        }
    }
    }







