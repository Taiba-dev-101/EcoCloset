package com.taibasharif.crafty.RecyclerView_ki_cheezain

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.taibasharif.crafty.Models.Repositories.Clothe
import com.taibasharif.crafty.Models.Repositories.Orders
import com.taibasharif.crafty.Views.Activities.ClotheSave.ClotheDetailActivity
import com.taibasharif.crafty.Views.Activities.OrderskiCheezain.OrderkiDetsActivity
import com.taibasharif.crafty.databinding.ClotheItemBinding
import com.taibasharif.crafty.databinding.OrderItemBinding

class ClotheAdapter( val items: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val binding = ClotheItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ClotheViewHolder(binding)
        }
        else{
            val binding =
                OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return OrderVH(binding)
        }
        }
        override fun getItemCount(): Int {
            return items.size
        }
        override fun getItemViewType(position: Int): Int {
            if (items.get(position) is Clothe) return 0
            if (items.get(position) is Orders) return 1
            return 2
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ClotheViewHolder) {
                val clothe = items.get(position) as Clothe
                holder.binding.titleC.text= clothe.title
                holder.binding.pricec.text = "RS: " + clothe.price
                holder.itemView.setOnClickListener {
                    holder.itemView.context.startActivity(
                        Intent(
                            holder.itemView.context,
                            ClotheDetailActivity::class.java
                        ).putExtra("data", Gson().toJson(clothe))
                    )
                }
            }
            else if (holder is OrderVH) {
                val order = items.get(position) as Orders
                holder.binding.productTitle.text = order.item?.title
                holder.binding.productPrice.text = order.item?.price.toString()
                holder.binding.status.text =order.status
                holder.itemView.setOnClickListener {
             holder.itemView.context.startActivity(
                    Intent(
                        holder.itemView.context,
                    OrderkiDetsActivity::class.java
                    ).putExtra("data", Gson().toJson(order))
               )
                }
        }}}


