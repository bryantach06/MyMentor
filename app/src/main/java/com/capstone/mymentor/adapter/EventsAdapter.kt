package com.capstone.mymentor.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyEvents
import com.capstone.mymentor.databinding.RvItemEventsBinding
import com.capstone.mymentor.ui.events.DetailEventsActivity
import com.capstone.mymentor.ui.events.DetailEventsActivity.Companion.EXTRA_TITLE

class EventsAdapter(private val listDummyEvents: ArrayList<DummyEvents>): RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    class ViewHolder(var binding: RvItemEventsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listDummyEvents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (title, location, time, price, date, speaker) = listDummyEvents[position]

        holder.binding.apply {
            tvRvEventTitle.text = title
            tvRvEventLocation.text = location
            tvRvEventTime.text = time
            tvEventsPrice.text = price
            tvEventsDate.text = date
            tvSpeaker.text = speaker
        }

        val dummyEvents = DummyEvents(title, location, time, price, date, speaker)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailEventsActivity::class.java)
            intent.putExtra(EXTRA_TITLE, dummyEvents)
            holder.itemView.context.startActivity(intent)
        }

    }

}