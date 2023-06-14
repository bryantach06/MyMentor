package com.capstone.mymentor.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.models.DummyMentors
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.RecommendedMentorsAdapter
import com.capstone.mymentor.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var rvMentors: RecyclerView
    private val list = ArrayList<DummyMentors>()

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

        rvMentors = view.findViewById(R.id.rv_mentors)
        rvMentors.setHasFixedSize(true)

        list.addAll(getListDummyMentors())
        showRecyclerList()
    }


    private fun getListDummyMentors(): ArrayList<DummyMentors> {
        val dummyMentorName = resources.getStringArray(R.array.dummy_mentor_name)
        val dummyMentorPosition = resources.getStringArray(R.array.dummy_mentor_position)
        val dummyMentorWorkplace = resources.getStringArray(R.array.dummy_mentor_workplace)
        val listDummyMentors = ArrayList<DummyMentors>()
        for (i in dummyMentorName.indices) {
            val dummyMentors = DummyMentors(dummyMentorName[i], dummyMentorPosition[i], dummyMentorWorkplace[i])
            listDummyMentors.add(dummyMentors)
        }
        return listDummyMentors
    }

    private fun showRecyclerList() {
        rvMentors.layoutManager = LinearLayoutManager(requireContext())
        val recommendedMentorsAdapter = RecommendedMentorsAdapter(list)
        rvMentors.adapter = recommendedMentorsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}