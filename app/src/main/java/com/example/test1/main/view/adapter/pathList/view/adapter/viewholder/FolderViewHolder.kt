package com.example.test1.main.view.adapter.pathList.view.adapter.viewholder

import com.example.test1.databinding.FolderItemBinding
import com.example.test1.main.view.adapter.pathList.model.FolderItem
import com.example.test1.main.view.adapter.pathList.view.adapter.PathListRVAdapter
import com.example.test1.utils.ThemeUtil
import com.example.test1.utils.extension.simpleLog

class FolderViewHolder(private val binding: FolderItemBinding) : PathViewHolder(binding.root) {

    fun bind(
        data: FolderItem,
        selectedPathIndex: Int,
        itemClickListener: PathListRVAdapter.InnerOnItemClickListener
    ) {
        simpleLog("${data.pathName} - $adapterPosition - $selectedPathIndex")
        val theme =
            if (data.isSelected)
                ThemeUtil.getFolderSelectedTheme()
            else
                ThemeUtil.getFolderTheme()

        // 1. icon
        binding.pathIcon.background = context.getDrawable(theme.t1)
        // 2. name
        binding.folderName.let { tv ->
            tv.text = data.pathName
            tv.setTextColor(context.getColor(theme.t2))
        }
        // 3. bg
        binding.root.let { cv ->
            cv.setCardBackgroundColor(context.getColor(theme.t3))
            cv.setOnClickListener {
                simpleLog("onItemClick($adapterPosition)")
                itemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }
}