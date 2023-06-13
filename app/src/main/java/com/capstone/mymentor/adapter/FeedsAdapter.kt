package com.capstone.mymentor.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyFeeds
import com.capstone.mymentor.databinding.RvItemFeedsBinding
import com.capstone.mymentor.ui.feeds.DetailFeedsActivity
import com.capstone.mymentor.ui.feeds.DetailFeedsActivity.Companion.EXTRA_NAME

class FeedsAdapter(private val listDummyFeeds: ArrayList<DummyFeeds>) :
    RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

    class ViewHolder(var binding: RvItemFeedsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RvItemFeedsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, caption) = listDummyFeeds[position]
        holder.binding.tvRvName.text = name
        holder.binding.tvRvCaption.text = caption
//        Glide.with(holder.itemView.context)
//            .load(photo)
//            .into(holder.binding.ivRvFeeds)
        val dummyFeeds = DummyFeeds(name, caption)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailFeedsActivity::class.java)
            intent.putExtra(EXTRA_NAME, dummyFeeds)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listDummyFeeds.size
}


//        val database = FirebaseFirestore.getInstance()
//        val email = Firebase.auth.currentUser?.email.toString()
//        database.collection("Users Posts").get().addOnSuccessListener { querySnapshot ->
//
//            for (document in querySnapshot.documents) {
//                val user = document.getString("User")
//                val title = document.getString("Title")
//                val image = document.getString("Image")
//
//                holder.binding.tvRvName.text = user
//                holder.binding.tvRvCaption.text = title
//                Glide.with(holder.itemView.context)
//                    .load(image)
//                    .into(holder.binding.ivRvFeeds)
//            }}