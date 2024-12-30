package com.taibasharif.crafty.Views.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.taibasharif.crafty.Models.Repositories.Chat
import com.taibasharif.crafty.RecyclerView_ki_cheezain.ChatListAdapter
import com.taibasharif.crafty.databinding.ActivityChatListBinding

class ChatListActivity : AppCompatActivity() {

     lateinit var binding: ActivityChatListBinding
    lateinit var adapter: ChatListAdapter
    private val chatList = mutableListOf<Chat>()
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)


         db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        // Set up RecyclerView

        adapter = ChatListAdapter(chatList) { chat ->
            // When a chat is clicked, open the chat activity
            val intent = Intent(this, AdminChatActivity::class.java)
            intent.putExtra("chatId", chat.sender)  // Passing user ID to load chat
            startActivity(intent)
        }

        binding.rvchatlist.adapter = adapter
        binding.rvchatlist.layoutManager = LinearLayoutManager(this)

        loadChats()
    }




    private fun loadChats() {
        db.collection("chats")// Reference to the 'chats' collection
            .get()
            .addOnSuccessListener { querySnapshot ->
                Toast.makeText(
                    this@ChatListActivity,
                    querySnapshot.size().toString(),
                    Toast.LENGTH_SHORT
                ).show()

                chatList.clear()  // Clear the existing list before adding new data
                for (document in querySnapshot) {
                    // Fetch the latest message from the 'messages' subcollection
                    val chatId = document.id
                    var user = auth.currentUser
                    val currentTimeMillis = System.currentTimeMillis()

// Format the timestamp to a readable time

                    val chat = Chat(sender = chatId, text = "", naam = user?.displayName!! , timestamp =System.currentTimeMillis())
                    chatList.add(chat)
                    db.collection("chats").document(chatId).collection("messages")
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .limit(1)  // Get the latest message
                        .get()
                        .addOnSuccessListener { messageSnapshot ->
                            if (!messageSnapshot.isEmpty) {
                                val messageDoc = messageSnapshot.documents.first()
                                val message = messageDoc.toObject(Chat::class.java)
                                if (message != null) {
                                    // Create a Chat object with the latest message details
                                    chatList.add(chat)
                                }
                            }
                            adapter.notifyDataSetChanged()  // Notify the adapter of the new data
                        }
                        .addOnFailureListener { e ->
                            //Log.e("ChatListActivity", "Error fetching messages", e)
                            Toast.makeText(
                                this,
                                "Error fetching messages: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }}}}











