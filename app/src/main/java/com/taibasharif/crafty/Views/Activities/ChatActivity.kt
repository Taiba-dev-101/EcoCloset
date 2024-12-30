package com.taibasharif.crafty.Views.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.taibasharif.crafty.Models.Repositories.Chat
import com.taibasharif.crafty.RecyclerView_ki_cheezain.ChatAdapter
import com.taibasharif.crafty.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter
    private val messageList = mutableListOf<Chat>()

    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val chatRef = FirebaseFirestore.getInstance().collection("chats").document(userId.toString()).collection("messages")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewBinding
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
val user=auth.currentUser
        // Set up RecyclerView
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(messageList)
        binding.recyclerViewChat.adapter = adapter

        // Load messages in real-time
        loadMessages()

        // Send message when button is clicked
        binding.buttonSend.setOnClickListener {
            val messageText = binding.editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val message = Chat("user", messageText, user?.displayName!!, System.currentTimeMillis())

                // Add message to Firestore
                chatRef.add(message).addOnSuccessListener {
                    Log.d("ChatActivity", "Message sent successfully")
                    FirebaseFirestore.getInstance().collection("chats").document(userId.toString()).set(
                        mapOf("name" to auth.currentUser?.displayName)
                    )
                    binding.editTextMessage.text.clear()
                }.addOnFailureListener { e ->
                    Log.e("ChatActivity", "Error sending message", e)
                }
            }
        }
    }

    private fun loadMessages() {
        chatRef.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    Log.e("ChatActivity", "Error loading messages", exception)
                    return@addSnapshotListener
                }

                messageList.clear()
                querySnapshot?.forEach { document ->
                    val message = document.toObject(Chat::class.java)
                    messageList.add(message)
                }
                adapter.notifyDataSetChanged()
                binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
            }
    }
}
