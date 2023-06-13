package com.capstone.mymentor.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.capstone.mymentor.models.Messages
import androidx.recyclerview.widget.RecyclerView.*
import com.capstone.mymentor.R
import com.capstone.mymentor.databinding.MessageChatboxBinding
import com.capstone.mymentor.ui.chat.DetailChatActivity.Companion.ANONYMOUS
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ChatMessagesAdapter(
    private val options: FirebaseRecyclerOptions<Messages>,
    private val getUserName: () -> String
) : FirebaseRecyclerAdapter<Messages, ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.message_chatbox, parent, false)
        val binding = MessageChatboxBinding.bind(view)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Messages) {
        (holder as MessageViewHolder).bind(model)
    }

    override fun getItemViewType(position: Int): Int {
        return if (options.snapshots[position].text != null) VIEW_TYPE_TEXT else VIEW_TYPE_IMAGE
    }

    inner class MessageViewHolder(private val binding: MessageChatboxBinding) :
        ViewHolder(binding.root) {
        fun bind(item: Messages) {
            binding.messageTextView.text = item.text
            val userName = getUserName()
            setTextColor(userName, binding.messageTextView)

//            binding.messengerTextView.text = item.name ?: ANONYMOUS
//            binding.messengerImageView.setImageResource(R.drawable.baseline_account_circle_black_36dp)
        }

        private fun setTextColor(userName: String?, textView: TextView) {
            if (userName != ANONYMOUS && userName != null) {
                textView.setBackgroundResource(R.drawable.rounded_message_purple)
                textView.setTextColor(Color.WHITE)
            } else {
                textView.setBackgroundResource(R.drawable.rounded_message_gray)
                textView.setTextColor(Color.BLACK)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_TEXT = 1
        const val VIEW_TYPE_IMAGE = 2
    }
}