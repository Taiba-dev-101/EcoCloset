package com.taibasharif.crafty

import com.taibasharif.crafty.AdminChatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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



//
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
                    val chat = Chat(sender = chatId, text = "", timestamp = 0)
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


//    private fun loadChats() {
//        db.collection("chats")
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                if (querySnapshot.isEmpty) {
//                    Toast.makeText(this, "No chats found in Firestore.", Toast.LENGTH_SHORT).show()
//                    return@addOnSuccessListener
//                }
//
//                Toast.makeText(this, "Chats found: ${querySnapshot.size()}", Toast.LENGTH_SHORT).show()
//
//                chatList.clear()  // Clear the existing list before adding new data
//                for (document in querySnapshot) {
//                    val chatId = document.id
//                    val chat = Chat(sender = chatId, text = "", timestamp = 0)
//                    chatList.add(chat)
//                }
//                adapter.notifyDataSetChanged()
//                Toast.makeText(this, "Chat list size: ${chatList.size}", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(this, "Error fetching chats: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }


//    private fun loadChats() {
//        db.collection("chats")
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                if (querySnapshot.isEmpty) {
//                    Toast.makeText(this, "No chats found in Firestore.", Toast.LENGTH_SHORT).show()
//                    return@addOnSuccessListener
//                }
//
//                Toast.makeText(this, "Chats found: ${querySnapshot.size()}", Toast.LENGTH_SHORT).show()
//
//                chatList.clear()  // Clear the existing list before adding new data
//                for (document in querySnapshot) {
//                    val chatId = document.id
//                    val chat = Chat(sender = chatId, text = "", timestamp = 0)
//                    chatList.add(chat)
//                }
//                adapter.notifyDataSetChanged()
//                Toast.makeText(this, "Chat list size: ${chatList.size}", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(this, "Error fetching chats: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }


//    private fun loadChats() {
//        // Dummy data for testing
//        val dummyData = listOf(
//            Chat(sender = "user1", text = "Hello, how are you?", timestamp = System.currentTimeMillis()),
//            Chat(sender = "user2", text = "I need help with my order.", timestamp = System.currentTimeMillis() - 60000),
//            Chat(sender = "user3", text = "Can you assist me with my query?", timestamp = System.currentTimeMillis() - 120000),
//            Chat(sender = "user4", text = "Is my order shipped?", timestamp = System.currentTimeMillis() - 180000)
//        )
//
//        // Add the dummy data to the chatList
//        chatList.clear()
//        chatList.addAll(dummyData)
//
//        // Notify the adapter that data has changed
//        adapter.notifyDataSetChanged()
//    }







