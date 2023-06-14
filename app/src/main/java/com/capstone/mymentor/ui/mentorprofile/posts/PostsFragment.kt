package com.capstone.mymentor.ui.mentorprofile.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.MentorPostsAdapter
import com.capstone.mymentor.databinding.FragmentExperiencesBinding
import com.capstone.mymentor.databinding.FragmentPostsBinding
import com.capstone.mymentor.models.DummyExperiences

class PostsFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null
    private lateinit var rvPosts: RecyclerView

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPosts = view.findViewById(R.id.rv_posts)
        rvPosts.setHasFixedSize(true)

        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvPosts.layoutManager = GridLayoutManager(requireContext(), 3)
//        val mentorPostsAdapter = MentorPostsAdapter(list)
//        rvPosts.adapter = mentorPostsAdapter
    }
}