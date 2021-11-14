package com.example.test1.main.view.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.databinding.PathPageItemBinding
import com.example.test1.main.model.FileItem
import com.example.test1.main.model.FolderItem
import com.example.test1.main.model.PathPageItem

// Many Pages
class ManyPageRVAdapter : RecyclerView.Adapter<ManyPageRVAdapter.ViewHolder>() {

    private val dataSet: MutableList<PathPageItem> = mutableListOf()
    private lateinit var mContext: Context
    private lateinit var mClickListener: ManyPageRVAdapter.OnItemClickListener

    inner class ViewHolder(val binding: PathPageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickListener {
        fun onFolderClick(clickedPathName: String, clickFromPageNum: Int)
        fun onFileClick(clickedPathName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val binding =
            PathPageItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        binding.root.layoutParams.width = parent.width / 2
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /**
         * scroll to the previous position after refresh Pages
         */
        fun setupLayoutManager() {
            // get previous layout status if have
            val preRVState = holder.binding.pathRV.layoutManager?.onSaveInstanceState()
            holder.binding.pathRV.layoutManager = LinearLayoutManager(mContext).apply {
                // set preview layout status if have
                onRestoreInstanceState(preRVState)
            }
        }

        fun setupAdapter() {
            val whichPage = position
            val pathRVAdapter = OnePageRVAdapter().apply {
                setOnItemClickListener(object : OnePageRVAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // the item which be clicked
                        dataSet[holder.adapterPosition].pathItems[position].apply {
                            when (this) {
                                is FolderItem -> {
                                    mClickListener.onFolderClick(filePath, whichPage)
                                }
                                is FileItem -> {
                                    mClickListener.onFileClick(filePath)
                                }
                            }
                        }
                    }
                })
            }
            holder.binding.pathRV.adapter = pathRVAdapter
        }

        fun setupDataSet() {
            (holder.binding.pathRV.adapter as OnePageRVAdapter).setDataSet(dataSet[position].pathItems)
        }

        fun  setupOnePage() {
            setupLayoutManager()
            setupAdapter()
            setupDataSet()
        }

        setupOnePage()

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setUpDataSet(inputDataSet: List<PathPageItem>) {
        dataSet.clear()
        dataSet.addAll(inputDataSet)
    }

    fun setOnItemClickListener(clickListener: ManyPageRVAdapter.OnItemClickListener) {
        this.mClickListener = clickListener
    }

}