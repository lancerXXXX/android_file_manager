package com.example.test1.main.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.databinding.PathPageItemBinding
import com.example.test1.main.model.PathPageItem

class PathPagesRVAdapter : RecyclerView.Adapter<PathPagesRVAdapter.ViewHolder>() {

    private val dataSet: MutableList<PathPageItem> = mutableListOf()
    private lateinit var mContext: Context
    private lateinit var mClickListener: PathPagesRVAdapter.OnItemClickListener

    inner class ViewHolder(val binding: PathPageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickListener {
        fun onItemClick(clickedPathName: String, clickFromPageNum: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val binding =
            PathPageItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        binding.root.layoutParams.width = parent.width / 2
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun setupLayoutManager() {
            // get previous layout status if have
            val preRVState = holder.binding.pathRV.layoutManager?.onSaveInstanceState()
            holder.binding.pathRV.layoutManager = LinearLayoutManager(mContext).apply {
                // set preview layout status if have
                onRestoreInstanceState(preRVState)
            }
        }

        fun setupAdapter() {
            val pathRVAdapter = PathRVAdapter()
            val whichPage = position
            pathRVAdapter.setOnItemClickListener(object : PathRVAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    // the item which be clicked
                    dataSet[holder.adapterPosition].pathItems[position].filePath.let {
                        Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
                        mClickListener.onItemClick(it, whichPage)
                    }
                }
            })
            holder.binding.pathRV.adapter = pathRVAdapter
        }

        fun setupDataSet() {
            (holder.binding.pathRV.adapter as PathRVAdapter).setDataSet(dataSet[position].pathItems)
        }

        setupLayoutManager()
        setupAdapter()
        setupDataSet()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setUpDataSet(inputDataSet: List<PathPageItem>) {
        dataSet.clear()
        dataSet.addAll(inputDataSet)
    }

    fun notifyPathRVDataSetChanged() {
    }

    fun setOnItemClickListener(clickListener: PathPagesRVAdapter.OnItemClickListener) {
        this.mClickListener = clickListener
    }

}