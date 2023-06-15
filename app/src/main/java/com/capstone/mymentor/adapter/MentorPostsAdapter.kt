package com.capstone.mymentor.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyMentorPosts
import com.capstone.mymentor.databinding.ItemRowPostsBinding
import com.capstone.mymentor.ui.feeds.DetailFeedsActivity
import com.capstone.mymentor.ui.feeds.DetailFeedsActivity.Companion.EXTRA_NAME
import com.capstone.mymentor.ui.profile.mentor.posts.DetailMentorPostsActivity
import com.capstone.mymentor.ui.profile.mentor.posts.DetailMentorPostsActivity.Companion.EXTRA_TITLE


class MentorPostsAdapter(private val listDummyMentorPosts: ArrayList<DummyMentorPosts>) : RecyclerView.Adapter<MentorPostsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowPostsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowPostsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, topic) = listDummyMentorPosts[position]
        holder.binding.tvMentorpostTitle.text = title

        val dummyPosts = DummyMentorPosts(title, topic)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailMentorPostsActivity::class.java)
            intent.putExtra(EXTRA_TITLE, dummyPosts)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listDummyMentorPosts.size
}