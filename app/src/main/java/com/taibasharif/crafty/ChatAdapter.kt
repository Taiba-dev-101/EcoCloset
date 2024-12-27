package com.taibasharif.crafty

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taibasharif.crafty.databinding.ChatItemBinding

class ChatAdapter(private val chats: List<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    // Custom ViewHolder class with ViewBinding
    class ChatViewHolder(val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = chats[position]

        // Set the message text
        holder.binding.textViewMessage.text = message.text

        // Align the message based on the sender
        if (message.sender == "user") {
            holder.binding.textViewMessage.setTextColor(Color.BLACK) // User message color
            holder.binding.textViewMessage.gravity = Gravity.START // Align left for user
        } else {
            holder.binding.textViewMessage.setTextColor(Color.BLUE) // Admin message color
            holder.binding.textViewMessage.gravity = Gravity.END // Align right for admin
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }
}
