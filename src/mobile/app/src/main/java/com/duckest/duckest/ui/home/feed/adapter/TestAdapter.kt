package com.duckest.duckest.ui.home.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.duckest.duckest.R
import com.duckest.duckest.databinding.ItemTestPreviewBinding

class TestAdapter(
    private val items: List<TestItem>,
    private val listener: TestItemListener
) :
    RecyclerView.Adapter<TestAdapter.TestHolder>() {
    interface TestItemListener {
        fun onClickedTest(imageId: Int, testId: Int)
    }

    inner class TestHolder(val binding: ItemTestPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TestItem) {
            binding.root.setOnClickListener {
                listener.onClickedTest(item.icon, item.title)
            }
            binding.title.setText(item.title)
            binding.photo.setImageResource(item.icon)
            if (item.done) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        return TestHolder(
            ItemTestPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


}