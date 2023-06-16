package com.capstone.mymentor.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mymentor.DummyFeeds
import com.capstone.mymentor.databinding.RvItemFeedsBinding
import com.capstone.mymentor.ui.feeds.DetailFeedsActivity
import com.capstone.mymentor.ui.feeds.DetailFeedsActivity.Companion.EXTRA_NAME

class FeedsAdapter(private val listDummyFeeds: ArrayList<DummyFeeds>, private val context: Context) :
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
        val dummyFeeds = DummyFeeds(name, caption)

        holder.binding.imageButton.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Feature under development!")
                setMessage("Liked Posts feature will be available upon future development!")
                setPositiveButton("Close", null)
                create()
                show()
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailFeedsActivity::class.java)
            intent.putExtra(EXTRA_NAME, dummyFeeds)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listDummyFeeds.size
}