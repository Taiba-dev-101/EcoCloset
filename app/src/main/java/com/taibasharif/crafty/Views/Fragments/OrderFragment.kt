package com.taibasharif.crafty.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.taibasharif.crafty.Models.Repositories.Orders
import com.taibasharif.crafty.R
import com.taibasharif.crafty.RecyclerView_ki_cheezain.ClotheAdapter
import com.taibasharif.crafty.ViewModels.OrderFragVM
import com.taibasharif.crafty.databinding.FragmentOrderBinding
import kotlinx.coroutines.launch


class OrderFragment: Fragment(){
    lateinit var adapter: ClotheAdapter
    lateinit var binding: FragmentOrderBinding
    lateinit var viewModel: OrderFragVM
    val items = ArrayList<Orders>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ClotheAdapter(items)
        binding.rv2.adapter = adapter
        binding.rv2.layoutManager = LinearLayoutManager(context)

        viewModel = OrderFragVM()
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
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                   // Toast.makeText(context,"${it.size}",Toast.LENGTH_SHORT).show()
                }
            }
}}}