package com.capstone.mymentor.ui.chat

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mymentor.ButtonObserver
import com.capstone.mymentor.DummyChat
import com.capstone.mymentor.ScrollToBottomObserver
import com.capstone.mymentor.adapter.ChatMessagesAdapter
import com.capstone.mymentor.databinding.ActivityDetailChatBinding
import com.capstone.mymentor.models.Messages
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DetailChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailChatBinding
    private lateinit var manager: LinearLayoutManager
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: ChatMessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.btnBack.setOnClickListener {
            finish()
        }

        if (BuildConfig.DEBUG) {
            Firebase.database.useEmulator("10.0.2.2", 9000)
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.storage.useEmulator("10.0.2.2", 9199)
        }

        db = Firebase.database
        val messagesRef = db.reference.child(MESSAGES_CHILD)

        val options = FirebaseRecyclerOptions.Builder<Messages>()
            .setQuery(messagesRef, Messages::class.java)
            .build()
        adapter = ChatMessagesAdapter(options) { getUserName() }
        binding.progressBar.visibility = ProgressBar.INVISIBLE
        manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager
        binding.messageRecyclerView.adapter = adapter

        adapter.registerAdapterDataObserver(
            ScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
        )

        val detail = intent.getParcelableExtra<DummyChat>(EXTRA_NAME_CHAT)
        binding.apply {
            tvChatroomName.text = detail?.name
        }

        binding.edMessage.addTextChangedListener(ButtonObserver(binding.sendButton))

        binding.sendButton.setOnClickListener {
            val message = Messages(
                binding.edMessage.text.toString(),
                getUserName(),
            )
            db.reference.child(MESSAGES_CHILD).push().setValue(message)
            Log.d("detailchat", db.reference.child(MESSAGES_CHILD).toString())
            Log.d("user name", getUserName())
            binding.edMessage.setText("")
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    private fun getUserName(): String {
        auth = Firebase.auth
        val user = auth.currentUser?.uid
        if (user != null) {
            val firebase = FirebaseFirestore.getInstance().collection("users").document(user).get()
            firebase.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val name = task.result?.getString("Name")
                    if (name != null) {
                        return@addOnCompleteListener
                    } else {
                        // Handle the case where name is null
                    }
                } else {
                    // Handle the case where task execution failed
                }
            }
        }
        return ANONYMOUS
    }

    companion object {
        const val EXTRA_NAME_CHAT = "extra_name"
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
    }
}