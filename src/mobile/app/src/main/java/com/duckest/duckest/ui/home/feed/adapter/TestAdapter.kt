package com.duckest.duckest.ui.home.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.duckest.duckest.R
import com.duckest.duckest.data.domain.TestLevelProgress
import com.duckest.duckest.data.domain.TestProgress
import com.duckest.duckest.databinding.ItemTestPreviewBinding

class TestAdapter(
    var items: List<TestProgress>,
    private val listener: TestItemListener
) :
    RecyclerView.Adapter<TestAdapter.TestHolder>() {
    interface TestItemListener {
        fun onClickedTest(testLevels: List<TestLevelProgress>?, testType: String?, imgUrl: String?)
    }

    inner class TestHolder(val binding: ItemTestPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TestProgress) {
            binding.root.setOnClickListener {
                listener.onClickedTest(item.testLevelProgress, item.testType, item.imageUrl)
            }

            binding.title.setText(item.testType)
            Glide.with(binding.photo)
                .load(item.imageUrl)
                .placeholder(
                    ResourcesCompat.getDrawable(
                        binding.root.resources,
                        R.mipmap.img_no_photo,
                        null
                    )
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.photo)
            //binding.photo.setImageResource(item.icon)
            item.isLevelCompleted?.let {
                if (it) {
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