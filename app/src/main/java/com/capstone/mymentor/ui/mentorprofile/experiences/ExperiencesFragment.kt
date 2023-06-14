package com.capstone.mymentor.ui.mentorprofile.experiences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.MentorExperiencesAdapter
import com.capstone.mymentor.databinding.FragmentExperiencesBinding
import com.capstone.mymentor.models.DummyExperiences

class ExperiencesFragment : Fragment() {

    private var _binding: FragmentExperiencesBinding? = null
    private lateinit var rvExperiences: RecyclerView
    private val list = ArrayList<DummyExperiences>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExperiencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        rvExperiences = view.findViewById(R.id.rv_experiences)
        rvExperiences.setHasFixedSize(true)

        list.addAll(getListDummyExperiences())
        showRecyclerList()

    }

    private fun getListDummyExperiences(): ArrayList<DummyExperiences> {
        val dummyPosition = resources.getStringArray(R.array.dummy_position)
        val dummyPlace = resources.getStringArray(R.array.dummy_place)
        val dummyStartDate = resources.getStringArray(R.array.dummy_start_date)
        val dummyEndDate = resources.getStringArray(R.array.dummy_end_date)
        val dummyLogo = resources.getStringArray(R.array.dummy_logo)
        val listDummyExperiences = ArrayList<DummyExperiences>()
        for (i in dummyPosition.indices) {
            val dummyExperiences = DummyExperiences(dummyPosition[i], dummyPlace[i], dummyStartDate[i], dummyEndDate[i], dummyLogo[i])
            listDummyExperiences.add(dummyExperiences)
        }
        return listDummyExperiences
    }

    private fun showRecyclerList() {
        rvExperiences.layoutManager = LinearLayoutManager(requireContext())
        val mentorExperiencesAdapter = MentorExperiencesAdapter(list)
        rvExperiences.adapter = mentorExperiencesAdapter
    }
}