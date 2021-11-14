package com.example.test1.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.test1.base.model.PathItem
import com.example.test1.databinding.FileItemBinding
import com.example.test1.databinding.FolderItemBinding
import com.example.test1.main.model.FileItem
import com.example.test1.main.model.FolderItem
import android.view.animation.AlphaAnimation




// One Page
class OnePageRVAdapter : RecyclerView.Adapter<OnePageRVAdapter.ViewHolder>() {

    private val dataSet: MutableList<PathItem> = mutableListOf()
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    companion object {
        const val DIRECTORY_TYPE_FOLDER = 0
        const val DIRECTORY_TYPE_FILE = 1
    }

    inner class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = when (viewType) {
            DIRECTORY_TYPE_FOLDER -> FolderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            DIRECTORY_TYPE_FILE -> FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            else -> FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.binding is FolderItemBinding) {
            holder.binding.apply {
                pathName.text = dataSet[position].pathName
                root.setOnClickListener {
                    itemClickListener.onItemClick(it, position)
                }
            }
        }
        else if (holder.binding is FileItemBinding) {
            holder.binding.apply {
                fileName.text = dataSet[position].pathName
                root.setOnClickListener {
                    itemClickListener.onItemClick(it, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setDataSet(inputDataSet: List<PathItem>) {
        dataSet.clear()
        dataSet.addAll(inputDataSet)
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position]) {
            is FolderItem -> DIRECTORY_TYPE_FOLDER
            is FileItem -> DIRECTORY_TYPE_FILE
            else -> DIRECTORY_TYPE_FILE
        }
    }

    fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }
}