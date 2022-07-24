package com.example.test1.main.view.adapter.pathList.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.databinding.PathListContainerBinding
import com.example.test1.main.model.FolderItem
import com.example.test1.main.view.adapter.pathList.view.adapter.PathListRVAdapter
import com.example.test1.utils.extension.simpleLog

/**
 * TODO: document your custom view class.
 */
class PathListContainer : LinearLayout {

    private lateinit var binding: PathListContainerBinding
    private val adapter = PathListRVAdapter().apply {
        this.setOnItemClickListener(object : PathListRVAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
            }

            override fun onItemLongClick(view: View, position: Int) {
            }

        })
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    private fun init(context: Context) {
        this.simpleLog("[PathListContainer] init")
        binding = PathListContainerBinding.inflate(LayoutInflater.from(context), this, true)
        binding.root.layoutManager = LinearLayoutManager(context)
        binding.root.adapter = adapter
        val list = listOf<FolderItem>(
            FolderItem(0, "1", "1"),
            FolderItem(0, "2", "2")
        )
        adapter.setDataSet(list)
        adapter.notifyDataSetChanged()
    }
}