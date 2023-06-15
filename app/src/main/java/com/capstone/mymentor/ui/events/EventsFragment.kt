package com.capstone.mymentor.ui.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyEvents
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.EventsAdapter
import com.capstone.mymentor.databinding.FragmentEventsBinding

class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private lateinit var rvEvents: RecyclerView
    private val listEvents = ArrayList<DummyEvents>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        _binding?.let { binding ->
            rvEvents = binding.rvEvents
            rvEvents.setHasFixedSize(true)
        }

        getListDummyEvents()
        showRecyclerList()

        binding.fabCalendar.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Feature under development!")
                setMessage("Events Calendar feature will be available upon future development!")
                setPositiveButton("Close", null)
                create()
                show()
            }
        }

    }

    private fun getListDummyEvents(): ArrayList<DummyEvents> {
        val dummyEventsTitle = resources.getStringArray(R.array.dummy_feeds_caption)
        val dummyEventsLocation = resources.getStringArray(R.array.dummy_events_location)
        val dummyEventsTime = resources.getStringArray(R.array.dummy_events_time)
        val dummyEventsPrice = resources.getStringArray(R.array.dummy_events_price)
        val dummyEventsDate = resources.getStringArray(R.array.dummy_events_date)
        val dummyEventsSpeaker = resources.getStringArray(R.array.dummy_events_speaker)
        for (i in dummyEventsTitle.indices) {
            val dummyEvents = DummyEvents(
                dummyEventsTitle[i],
                dummyEventsLocation[i],
                dummyEventsTime[i],
                dummyEventsPrice[i],
                dummyEventsDate[i],
                dummyEventsSpeaker[i],
            )
            listEvents.add(dummyEvents)
        }
        return listEvents
    }

    private fun showRecyclerList() {
        rvEvents.layoutManager = LinearLayoutManager(requireContext())
        val eventsAdapter = EventsAdapter(listEvents)
        rvEvents.adapter = eventsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}