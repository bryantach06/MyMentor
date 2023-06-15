package com.capstone.mymentor.ui.profile.mentor.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyMentorPosts
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.MentorPostsAdapter
import com.capstone.mymentor.databinding.FragmentExperiencesBinding
import com.capstone.mymentor.databinding.FragmentPostsBinding
import com.capstone.mymentor.models.DummyExperiences
import com.capstone.mymentor.models.DummyMentors

class PostsFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null
    private lateinit var rvPosts: RecyclerView
    private val listPosts = ArrayList<DummyMentorPosts>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPosts = view.findViewById(R.id.rv_posts)
        rvPosts.setHasFixedSize(true)

        showRecyclerList()
        getListDummyPosts()
    }

    private fun getListDummyPosts(): ArrayList<DummyMentorPosts> {
        val title = resources.getStringArray(R.array.dummy_feeds_caption)
        val topic = resources.getStringArray(R.array.dummy_topic)
        for (i in title.indices) {
            val dummyPosts = DummyMentorPosts(title[i], topic[i])
            listPosts.add(dummyPosts)
        }
        return listPosts
    }

    private fun showRecyclerList() {
        rvPosts.layoutManager = GridLayoutManager(requireContext(), 2)
        val mentorPostsAdapter = MentorPostsAdapter(listPosts)
        rvPosts.adapter = mentorPostsAdapter
    }
}