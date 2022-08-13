package com.example.test1.main.view.adapter.pathList.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.databinding.PathListContainerBinding
import com.example.test1.main.model.PageData
import com.example.test1.main.view.adapter.pathList.model.FileItem
import com.example.test1.main.view.adapter.pathList.model.FolderItem
import com.example.test1.main.view.adapter.pathList.view.adapter.PathListRVAdapter
import com.example.test1.main.view.adapter.pathList.viewmodel.PathListViewModel
import com.example.test1.utils.extension.simpleLog
import java.util.concurrent.atomic.AtomicLong

/**
 * TODO: document your custom view class.
 */
class PathListContainer : LinearLayout{
    /* ================= view =================== */
    private lateinit var binding: PathListContainerBinding
    /* ================= viewModel ============== */
    private lateinit var viewModel: PathListViewModel

    private val pathListContentIdCreator = AtomicLong()
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

    override fun onAttachedToWindow() {
        viewModel = ViewModelProvider(findViewTreeViewModelStoreOwner()!!)[PathListViewModel::class.java]
        super.onAttachedToWindow()
    }

    private fun init(context: Context) {
        simpleLog("init[$this]")
        initRVList()
        pathListContentIdCreator.set(0)
    }

    fun setDependency(
        pathList1: PageData,
        clickListener: PathListRVAdapter.OnItemClickListener
    ) {
        simpleLog("setDependency-$pathList1")
        this.parentPath = pathList1.path
        adapter.setOnItemClickListener(clickListener, object :
            PathListRVAdapter.InnerOnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                when(val temp = pathList1.pathItems[position]) {
                    is FileItem -> {

                    }
                    is FolderItem -> {
                        // set new clicked folder isSelected
                        pathList1.pathItems[position] = temp.copy(
                            contentId = pathListContentIdCreator.incrementAndGet(),
                            isSelected = true
                        )
                        // set original selected folder  not selected
                        when (val original = pathList1.pathItems.getOrNull(adapter.selectedPathIndex)) {
                            is FolderItem -> {
                                pathList1.pathItems[adapter.selectedPathIndex] = original.copy(
                                    contentId = pathListContentIdCreator.incrementAndGet(),
                                    isSelected = false
                                )
                            }
                            else -> { }
                        }
                        adapter.submitList(pathList1.pathItems.toMutableList())
                    }
                }
                adapter.selectedPathIndex = position
                clickListener.onItemClick(view, position)
            }

            override fun onItemLongClick(view: View, position: Int) {
                clickListener.onItemLongClick(view, position)
            }
        })
        adapter.selectedPathIndex = pathList1.selectedIndex
        adapter.submitList(pathList1.pathItems.toMutableList())
    }

    private fun initRVList() {
        simpleLog("initRVList")
        binding = PathListContainerBinding.inflate(LayoutInflater.from(context), this, true)
        binding.root.layoutManager = LinearLayoutManager(context)
        adapter = PathListRVAdapter()
        binding.root.adapter = adapter
    }

}