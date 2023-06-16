package com.capstone.mymentor.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.models.DummyMentors
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.RecommendedMentorsAdapter
import com.capstone.mymentor.data.response.MentorResultResponseItem
import com.capstone.mymentor.databinding.FragmentHomeBinding
import com.capstone.mymentor.ui.feeds.AddPostActivity
import com.capstone.mymentor.ui.login.LoginActivity
import com.capstone.mymentor.ui.profile.mentee.MenteeProfileActivity
import com.google.android.play.integrity.internal.l

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<HomeViewModel>()
//    private val list = ArrayList<DummyMentors>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        homeViewModel.listMentors.observe(viewLifecycleOwner) { email: List<MentorResultResponseItem> ->
            setMentorsData(email)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        showRecyclerList()

//        rvMentors = view.findViewById(R.id.rv_mentors)
//        rvMentors.setHasFixedSize(true)
//
//        list.addAll(getListDummyMentors())

        binding.ivButtonProfile.setOnClickListener {
            val intent = Intent(requireActivity(), MenteeProfileActivity::class.java)
            startActivity(intent)
        }
    }


//    private fun getListDummyMentors(): ArrayList<DummyMentors> {
//        val dummyMentorName = resources.getStringArray(R.array.dummy_mentor_name)
//        val dummyMentorPosition = resources.getStringArray(R.array.dummy_mentor_position)
//        val dummyMentorWorkplace = resources.getStringArray(R.array.dummy_mentor_workplace)
//        val listDummyMentors = ArrayList<DummyMentors>()
//        for (i in dummyMentorName.indices) {
//            val dummyMentors = DummyMentors(dummyMentorName[i], dummyMentorPosition[i], dummyMentorWorkplace[i])
//            listDummyMentors.add(dummyMentors)
//        }
//        return listDummyMentors
//    }

    private fun setMentorsData(listMentors: List<MentorResultResponseItem>) {
//        listMentors.sortedByDescending {
//            it.name
//        }
        val adapter = RecommendedMentorsAdapter(listMentors)
        binding.rvMentors.adapter = adapter
    }

    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvMentors.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}