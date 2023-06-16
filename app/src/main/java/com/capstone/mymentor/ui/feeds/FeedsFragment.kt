package com.capstone.mymentor.ui.feeds

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyFeeds
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.FeedsAdapter
import com.capstone.mymentor.databinding.FragmentFeedsBinding

class FeedsFragment : Fragment() {

    private var _binding: FragmentFeedsBinding? = null
    private lateinit var rvFeeds: RecyclerView
    private val listFeeds = ArrayList<DummyFeeds>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        _binding?.let { binding ->
            rvFeeds = binding.rvFeeds
            rvFeeds.setHasFixedSize(true)
        }

        getListDummyFeeds()
        showRecyclerList()

        binding.fabAddPost.setOnClickListener {
            val intent = Intent(requireActivity(), AddPostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getListDummyFeeds(): ArrayList<DummyFeeds> {
        val dummyFeedsName = resources.getStringArray(R.array.dummy_mentor_name)
        val dummyFeedsCaption = resources.getStringArray(R.array.dummy_feeds_caption)
        for (i in dummyFeedsName.indices) {
            val dummyFeeds = DummyFeeds(dummyFeedsName[i], dummyFeedsCaption[i], )
            listFeeds.add(dummyFeeds)
        }
        return listFeeds
    }

    private fun showRecyclerList() {
        rvFeeds.layoutManager = LinearLayoutManager(requireContext())
        val feedsAdapter = FeedsAdapter(listFeeds, requireContext())
        rvFeeds.adapter = feedsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}