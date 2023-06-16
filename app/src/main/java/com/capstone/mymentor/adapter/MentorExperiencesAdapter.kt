package com.capstone.mymentor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.databinding.ItemRowExperiencesBinding
import com.capstone.mymentor.models.DummyExperiences

class MentorExperiencesAdapter(private val listDummyExperiences: ArrayList<DummyExperiences>) : RecyclerView.Adapter<MentorExperiencesAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowExperiencesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowExperiencesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (workPosition, place, startDate, endDate) = listDummyExperiences[position]
        holder.binding.tvPosition.text = workPosition
        holder.binding.tvPlace.text = place
        holder.binding.tvStartDate.text = startDate
        holder.binding.tvEndDate.text = endDate
    }

    override fun getItemCount(): Int = listDummyExperiences.size

}