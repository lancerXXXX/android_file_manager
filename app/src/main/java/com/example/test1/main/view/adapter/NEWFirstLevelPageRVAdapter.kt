package com.example.test1.main.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.databinding.PathPageItemBinding
import com.example.test1.main.model.FileItem
import com.example.test1.main.model.FolderItem
import com.example.test1.main.model.PathPageItem

class NEWFirstLevelPageRVAdapter : ListAdapter<PathPageItem, NEWFirstLevelPageRVAdapter.ViewHolder>(TaskDiffCallback()) {

    private lateinit var context: Context
    private lateinit var clickListener: NEWFirstLevelPageRVAdapter.OnItemClickListener

    inner class ViewHolder(val binding: PathPageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickListener {
        fun onFolderClick(clickedPathName: String, clickFromPageNum: Int)
        fun onFileClick(clickedPathName: String)
        fun onFolderLongClick(clickedPathName: String, clickFromPageNum: Int)
        fun onFileLongClick(clickedPathName: String)
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<PathPageItem>() {

        override fun areItemsTheSame(oldItem: PathPageItem, newItem: PathPageItem): Boolean {
            return oldItem.pathItems == newItem.pathItems
        }

        override fun areContentsTheSame(oldItem: PathPageItem, newItem: PathPageItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = PathPageItemBinding.inflate(LayoutInflater.from(context), parent, false).also {
            it.root.layoutParams.width = parent.width / 2
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /**
         * scroll to the previous position after refresh first level page
         */
        fun setupLayoutManager() {
            // get previous layout status if have
            holder.binding.pathRV.layoutManager?.onSaveInstanceState().let { preRVStatus ->
                holder.binding.pathRV.layoutManager = LinearLayoutManager(context).apply {
                    // set previous layout status if have
                    onRestoreInstanceState(preRVStatus)
                }
            }
        }

        fun setupAdapter() {
            val whichPage = position
            val pathRVAdapter = SecondLevelPageRVAdapter().apply {
                setOnItemClickListener(object : SecondLevelPageRVAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        getItem(whichPage).let { whichPageDataSet ->
                            whichPageDataSet.pathItems[position].let { pathItem ->
                                when (pathItem) {
                                    is FolderItem -> {
                                        clickListener.onFolderClick(pathItem.filePath, whichPage)
                                        whichPageDataSet.selectedIndex = position
                                    }
                                    is FileItem -> {
                                        clickListener.onFileClick(pathItem.filePath)
                                    }
                                }
                            }
                        }
                    }

                    override fun onItemLongClick(view: View, position: Int) {
                        getItem(whichPage).pathItems[position].let { pathItem ->
                            when (pathItem) {
                                is FolderItem -> clickListener.onFolderLongClick(pathItem.filePath, whichPage)
                                is FileItem -> clickListener.onFileLongClick(pathItem.filePath)
                            }
                        }
                    }
                })
            }
            holder.binding.pathRV.adapter = pathRVAdapter
        }

        fun setupDataSet() {
            (holder.binding.pathRV.adapter as SecondLevelPageRVAdapter).apply {
                getItem(position).let { onePageItem ->
                    setDataSet(onePageItem.pathItems)
                    selectedPathIndex = onePageItem.selectedIndex
                }
            }
        }

        fun setupOnePage() {
            setupLayoutManager()
            setupAdapter()
            setupDataSet()
        }

        setupOnePage()
    }

    fun setOnItemClickListener(clickListener: NEWFirstLevelPageRVAdapter.OnItemClickListener) {
        this.clickListener = clickListener
    }

}