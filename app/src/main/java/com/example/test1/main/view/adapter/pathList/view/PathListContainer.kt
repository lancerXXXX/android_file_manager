package com.example.test1.main.view.adapter.pathList.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.databinding.PathListContainerBinding
import com.example.test1.main.view.adapter.pathList.model.FolderItem
import com.example.test1.main.view.adapter.pathList.model.PathData
import com.example.test1.main.view.adapter.pathList.view.adapter.PathListRVAdapter
import com.example.test1.utils.extension.simpleLog

/**
 * TODO: document your custom view class.
 */
class PathListContainer : LinearLayout{
    /* ================= view =================== */
    private lateinit var binding: PathListContainerBinding
    /* ================= data =================== */
    private var parentPath: String = ""
    /* ================= util =================== */
    private lateinit var adapter: PathListRVAdapter

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
        simpleLog("init[$this]")
        initRVList()
    }

    fun setDependency(
        pathList1: String,
        pathList: MutableList<PathData>,
        clickListener: PathListRVAdapter.OnItemClickListener
    ) {
        simpleLog("setDependency-$pathList1")
        this.parentPath = pathList1
        adapter.setOnItemClickListener(clickListener)
        adapter.submitList(pathList.toMutableList())
    }

    private fun initRVList() {
        simpleLog("initRVList")
        binding = PathListContainerBinding.inflate(LayoutInflater.from(context), this, true)
        binding.root.layoutManager = LinearLayoutManager(context)
        adapter = PathListRVAdapter()
        binding.root.adapter = adapter
    }

}