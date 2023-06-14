package com.capstone.mymentor.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        val (workPosition, place, startDate, endDate, logo) = listDummyExperiences[position]
        holder.binding.tvPosition.text = workPosition
        holder.binding.tvPlace.text = place
        holder.binding.tvStartDate.text = startDate
        holder.binding.tvEndDate.text = endDate
        Glide.with(holder.itemView.context)
            .load(logo)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(holder.binding.ivCompany)
    }

    override fun getItemCount(): Int = listDummyExperiences.size

}