package com.example.test1.main.view.adapter.pagelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.test1.databinding.PathPageItemBinding
import com.example.test1.main.model.PageData
import com.example.test1.main.view.adapter.pagelist.viewholder.PageViewHolder

class PageListRVAdapter : ListAdapter<PageData, PageViewHolder>(FirstLevelDiffCallback() ) {

    private lateinit var context: Context
    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onFolderClick(clickedPathName: String, clickFromPageNum: Int)
        fun onFileClick(clickedPathName: String)
        fun onFolderLongClick(clickedPathName: String, clickFromPageNum: Int)
        fun onFileLongClick(clickedPathName: String)
    }

    class FirstLevelDiffCallback : DiffUtil.ItemCallback<PageData>() {

        override fun areItemsTheSame(oldItem: PageData, newItem: PageData): Boolean {
            return oldItem == newItem || oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: PageData, newItem: PageData): Boolean {
            return oldItem.contentId == newItem.contentId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        context = parent.context
        val binding = PathPageItemBinding.inflate(LayoutInflater.from(context), parent, false).also {
            it.root.layoutParams.width = parent.width / 2
        }
        return PageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

}