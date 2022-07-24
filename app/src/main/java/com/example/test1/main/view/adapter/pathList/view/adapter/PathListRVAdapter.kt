package com.example.test1.main.view.adapter.pathList.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.test1.main.model.PathData
import com.example.test1.databinding.FileItemBinding
import com.example.test1.databinding.FolderItemBinding
import com.example.test1.main.model.FileItem
import com.example.test1.main.model.FolderItem
import com.example.test1.main.view.adapter.pathList.view.adapter.viewholder.FileViewHolder
import com.example.test1.main.view.adapter.pathList.view.adapter.viewholder.FolderViewHolder
import com.example.test1.main.view.adapter.pathList.view.adapter.viewholder.PathViewHolder


// One Page
class PathListRVAdapter : ListAdapter<PathData, PathViewHolder>(PathListDiffCallback()) {

    private val pathList: MutableList<PathData> = mutableListOf()
    private lateinit var itemClickListener: OnItemClickListener
    var selectedPathIndex = -1

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    class PathListDiffCallback: DiffUtil.ItemCallback<PathData>() {

        override fun areItemsTheSame(oldItem: PathData, newItem: PathData): Boolean {
            return oldItem == newItem || oldItem.fullPath == newItem.fullPath
        }

        override fun areContentsTheSame(oldItem: PathData, newItem: PathData): Boolean {
            return oldItem.contentId == newItem.contentId
        }
    }

    companion object {
        const val DIRECTORY_TYPE_FOLDER = 0
        const val DIRECTORY_TYPE_FILE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PathViewHolder {
        return when (viewType) {
            DIRECTORY_TYPE_FOLDER ->
                FolderViewHolder(FolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            DIRECTORY_TYPE_FILE ->
                FileViewHolder(FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else ->
                FolderViewHolder(FolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: PathViewHolder, position: Int) {
        when (holder) {
            is FileViewHolder -> {
                (pathList[position] as? FileItem)?.let { fileItem ->
                    holder.bind(fileItem, itemClickListener)
                }
            }
            is FolderViewHolder -> {
                (pathList[position] as? FolderItem)?.let { folderItem ->
                    holder.bind(folderItem , selectedPathIndex, itemClickListener)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return pathList.size
    }

    fun setDataSet(inputDataSet: List<PathData>) {
        pathList.clear()
        pathList.addAll(inputDataSet)
    }

    override fun getItemViewType(position: Int): Int {
        return when (pathList[position]) {
            is FolderItem -> DIRECTORY_TYPE_FOLDER
            is FileItem -> DIRECTORY_TYPE_FILE
            else -> DIRECTORY_TYPE_FOLDER
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }
}