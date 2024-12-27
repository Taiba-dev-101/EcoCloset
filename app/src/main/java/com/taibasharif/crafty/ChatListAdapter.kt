package com.taibasharif.crafty

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taibasharif.crafty.databinding.ChatlistItemBinding
import com.taibasharif.crafty.ChatListViewHolder
class ChatListAdapter(
    private val chatList: List<Chat>,
    private val onClick: (Chat) -> Unit
) : RecyclerView.Adapter<ChatListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val binding = ChatlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatListViewHolder(binding)
    }



    override fun getItemCount(): Int = chatList.size

    override  fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val chat = chatList[position]

        // Bind data to views
        holder.binding.textViewUserId.text = chat.sender // Display sender info (e.g., user ID)
        holder.binding.textViewLastMessage.text = chat.text // Display the last message

        // When the item is clicked, trigger the onClick callback to open the chat
        holder.itemView.setOnClickListener {
            onClick(chat)
        }
        Log.d("ChatListAdapter", "chatList size: ${chatList.size}")

    }
    }


