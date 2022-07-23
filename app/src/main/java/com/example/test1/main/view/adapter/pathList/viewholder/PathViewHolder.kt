package com.example.test1.main.view.adapter.pathList.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

sealed class PathViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val context: Context = itemView.context
}