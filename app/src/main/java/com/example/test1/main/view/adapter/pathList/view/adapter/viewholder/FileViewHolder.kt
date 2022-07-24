package com.example.test1.main.view.adapter.pathList.view.adapter.viewholder

import com.example.test1.databinding.FileItemBinding
import com.example.test1.main.model.PathData
import com.example.test1.main.view.adapter.pathList.view.adapter.PathListRVAdapter
import com.example.test1.utils.ThemeUtil

class FileViewHolder(private val binding: FileItemBinding): PathViewHolder(binding.root) {

    fun bind(data: PathData, itemClickListener: PathListRVAdapter.OnItemClickListener) {
        val theme = ThemeUtil.getFileTheme()
        // 1. icon
        binding.fileIcon.background = context.getDrawable(theme.t1)
        // 2. name
        binding.fileName?.let { tv ->
            tv.setTextColor(context.getColor(theme.t2))
            tv.text = data.pathName
        }
        // 3. item
        binding.root.setOnClickListener {
            itemClickListener.onItemClick(it, adapterPosition)
        }
    }

}