package com.example.test1.main.view.adapter.pathList.viewholder

import com.example.test1.databinding.FolderItemBinding
import com.example.test1.main.model.FolderItem
import com.example.test1.main.view.adapter.pathList.PathListRVAdapter
import com.example.test1.utils.ThemeUtil

class FolderViewHolder(private val binding: FolderItemBinding) : PathViewHolder(binding.root) {

    fun bind(
        data: FolderItem,
        selectedPathIndex: Int,
        itemClickListener: PathListRVAdapter.OnItemClickListener
    ) {
        val theme =
            if (adapterPosition == selectedPathIndex) ThemeUtil.getFolderSelectedTheme()
            else ThemeUtil.getFolderTheme()

        // 1. icon
        binding.pathIcon.background = context.getDrawable(theme.t1)
        // 2. name
        binding.folderName?.let { tv ->
            tv.text = data.pathName
            tv.setTextColor(context.getColor(theme.t2))
        }
        // 3. bg
        binding.root.let { cv ->
            cv.setCardBackgroundColor(context.getColor(theme.t3))
            cv.setOnClickListener {
                itemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }
}