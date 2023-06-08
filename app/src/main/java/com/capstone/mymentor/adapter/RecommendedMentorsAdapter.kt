package com.capstone.mymentor.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyMentors
import com.capstone.mymentor.databinding.ItemRowMentorBinding

class RecommendedMentorsAdapter(val listDummyMentors: ArrayList<DummyMentors>) : RecyclerView.Adapter<RecommendedMentorsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowMentorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowMentorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, workPosition, workplace) = listDummyMentors[position]
        holder.binding.tvMentorName.text = name
        holder.binding.tvMentorPosition.text = workPosition
        holder.binding.tvMentorWorkplace.text = workplace
//      Glide.with(holder.itemView.context)
//            .load(photo)
//            .into(holder.binding.ivMentor)

        if (position % 2 == 0) {
            holder.binding.cardView.setCardBackgroundColor(Color.parseColor("#4D2C5C"))
        } else {
            holder.binding.cardView.setCardBackgroundColor(Color.parseColor("#FA8886"))
        }
    }

    override fun getItemCount(): Int = listDummyMentors.size

}