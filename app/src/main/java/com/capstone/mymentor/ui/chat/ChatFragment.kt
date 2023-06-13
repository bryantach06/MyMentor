package com.capstone.mymentor.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyChat
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.ChatAdapter
import com.capstone.mymentor.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private lateinit var rvChat: RecyclerView
    private val listChat = ArrayList<DummyChat>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        rvChat = view.findViewById(R.id.rv_chat)
        rvChat.setHasFixedSize(true)

        listChat.addAll(getListDummyChats())
        showRecyclerList()
    }

    private fun getListDummyChats(): ArrayList<DummyChat> {
        val dummyChatName = resources.getStringArray(R.array.dummy_chat_name)
        val dummyChatMessage = resources.getStringArray(R.array.dummy_chat_message)
        for (i in dummyChatName.indices) {
            val dummyChats = DummyChat(dummyChatName[i], dummyChatMessage[i], )
            listChat.add(dummyChats)
        }
        return listChat
    }

    private fun showRecyclerList() {
        rvChat.layoutManager = LinearLayoutManager(requireContext())
        val chatAdapter = ChatAdapter(listChat)
        rvChat.adapter = chatAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}