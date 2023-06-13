package com.capstone.mymentor.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyChat
import com.capstone.mymentor.databinding.RvItemChatBinding
import com.capstone.mymentor.ui.chat.DetailChatActivity
import com.capstone.mymentor.ui.chat.DetailChatActivity.Companion.EXTRA_NAME_CHAT

class ChatAdapter(private val listDummyChat: ArrayList<DummyChat>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(var binding: RvItemChatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, chat) = listDummyChat[position]
        holder.binding.tvChatName.text = name
        holder.binding.tvChat.text = chat

        val dummyChat = DummyChat(name, chat)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailChatActivity::class.java)
            intent.putExtra(EXTRA_NAME_CHAT, dummyChat)
            holder.itemView.context.startActivity(intent)
        }
    }

}