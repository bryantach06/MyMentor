package com.capstone.mymentor.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.data.response.MentorResultResponseItem
import com.capstone.mymentor.databinding.ItemRowMentorBinding
import com.capstone.mymentor.ui.profile.mentor.MentorProfileActivity

class RecommendedMentorsAdapter(private val listMentors: List<MentorResultResponseItem>) : RecyclerView.Adapter<RecommendedMentorsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowMentorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowMentorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val (name, workPosition, workplace) = listMentors[position].mentorResultResponse
//        holder.binding.tvMentorName.text = name
//        holder.binding.tvMentorPosition.text = workPosition
//        holder.binding.tvMentorWorkplace.text = workplace

        holder.binding.apply {
            tvMentorName.text = listMentors[position].name
            tvMentorPosition.text = listMentors[position].workPosition
            tvRating.text = listMentors[position].rating.toString()
        }

        holder.binding.cardView.setOnClickListener{
            val intentMentorProfile = Intent(holder.binding.cardView.context, MentorProfileActivity::class.java)
            intentMentorProfile.putExtra(
                MentorProfileActivity.KEY_NAME,
                listMentors[holder.adapterPosition].name
            )
            intentMentorProfile.putExtra(
                MentorProfileActivity.KEY_POSITION,
                listMentors[holder.adapterPosition].workPosition
            )
            intentMentorProfile.putExtra(
                MentorProfileActivity.KEY_RATING,
                listMentors[holder.adapterPosition].rating.toString()
            )
            holder.binding.cardView.context.startActivity(intentMentorProfile)
        }
    }

    override fun getItemCount(): Int = listMentors.size

}