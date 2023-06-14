package com.capstone.mymentor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.databinding.ItemRowPostsBinding


class MentorPostsAdapter() : RecyclerView.Adapter<MentorPostsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowPostsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowPostsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        TODO()
//        Glide.with(holder.itemView.context)
//            .load(image)
//            .into(holder.binding.ivPosts)
    }

    override fun getItemCount(): Int { TODO()
    }
}