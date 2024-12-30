package com.taibasharif.crafty.Views.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import androidx.recyclerview.widget.LinearLayoutManager
import com.taibasharif.crafty.Models.Repositories.Chat
import com.taibasharif.crafty.RecyclerView_ki_cheezain.ChatAdapter
import com.taibasharif.crafty.databinding.ActivityAdminChatBinding
import java.text.SimpleDateFormat

class AdminChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminChatBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ChatAdapter
    private val messageList = mutableListOf<Chat>()

    private lateinit var chatId: String  // To hold the chat document ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityAdminChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the chatId passed from ChatListActivity
        chatId = intent.getStringExtra("chatId") ?: return

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Set up RecyclerView
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(messageList)
        binding.rvChat.adapter = adapter

        // Load messages in real-time
        loadMessages()

        // Send reply when the button is clicked
        binding.buttonSendReply.setOnClickListener {
            val replyText = binding.editTextReply.text.toString().trim()
            if (replyText.isNotEmpty()) {
                val message = Chat("admin", replyText, SimpleDateFormat("yyyy-MM-dd HH:mm a").format(System.currentTimeMillis()))

                // Add reply message to Firestore
                val chatRef = db.collection("chats").document(chatId).collection("messages")
                chatRef.add(message).addOnSuccessListener {
                    Log.d("AdminChatActivity", "Reply sent successfully")
                    binding.editTextReply.text.clear()
                }.addOnFailureListener { e ->
                    Log.e("AdminChatActivity", "Error sending reply", e)
                }
            }
        }
    }

    private fun loadMessages() {
        // Listen to all messages in the "messages" sub-collection for this chat
        val chatRef = db.collection("chats").document(chatId).collection("messages")
        chatRef.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    Log.e("AdminChatActivity", "Error loading messages", exception)
                    return@addSnapshotListener
                }

                messageList.clear()
                querySnapshot?.forEach { document ->
                    val message = document.toObject(Chat::class.java)
                    messageList.add(message)
                }
                adapter.notifyDataSetChanged()
                binding.rvChat.scrollToPosition(messageList.size - 1)
            }
    }
}

