package com.duckest.duckest.ui.home.testlevel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.duckest.duckest.R
import com.duckest.duckest.databinding.ItemTestLevelBinding

class LevelAdapter(
    private val items: List<LevelItem>,
    private val listener: LevelItemListener
) :
    RecyclerView.Adapter<LevelAdapter.LevelHolder>() {
    interface LevelItemListener {
        fun onClickedTest(testLevel: String, testPassed: Boolean, progress: Double)
    }

    inner class LevelHolder(val binding: ItemTestLevelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LevelItem) {
            binding.root.setOnClickListener {
                item.title?.let {
                    listener.onClickedTest(item.title, item.done!!, item.progress!!)
                }
            }
            binding.levelTitle.text = item.title
            binding.photo.setImageResource(item.icon)
            if (item.done!!) {
                binding.testCheck.setColorFilter(
                    ResourcesCompat.getColor(
                        binding.root.resources,
                        R.color.green,
                        null
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelHolder {
        return LevelHolder(
            ItemTestLevelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LevelHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}