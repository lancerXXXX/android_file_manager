package com.example.test1.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.databinding.PathItemBinding
import com.example.test1.main.model.PathItem

// One Page
class OnePageRVAdapter : RecyclerView.Adapter<OnePageRVAdapter.ViewHolder>() {

    private val dataSet: MutableList<PathItem> = mutableListOf()
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class ViewHolder(val binding: PathItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PathItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.pathName.text = dataSet[position].pathName
        holder.binding.root.setOnClickListener {
            itemClickListener.onItemClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setDataSet(inputDataSet: List<PathItem>) {
        dataSet.clear()
        dataSet.addAll(inputDataSet)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }
}