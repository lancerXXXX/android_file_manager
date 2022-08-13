package com.example.test1.main.view.adapter.pathList.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.databinding.PathListContainerBinding
import com.example.test1.main.model.PageData
import com.example.test1.main.view.adapter.pathList.model.FileItem
import com.example.test1.main.view.adapter.pathList.model.FolderItem
import com.example.test1.main.view.adapter.pathList.view.adapter.PathListRVAdapter
import com.example.test1.main.view.adapter.pathList.viewmodel.PathListViewModel
import com.example.test1.utils.extension.simpleLog
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.concurrent.atomic.AtomicLong

/**
 * TODO: document your custom view class.
 */
class PathListContainer : LinearLayout {
    /* ================= view =================== */
    private lateinit var binding: PathListContainerBinding

    /* ================= viewModel ============== */
    private var viewModel: PathListViewModel = PathListViewModel()

    /* ================= data =================== */
    private var parentPath: String = ""

    /* ================= util =================== */
    private val pathListContentIdCreator = AtomicLong()
    private var clickListener: PathListRVAdapter.OnItemClickListener? = null
    private var innerOnItemClickListener = object :
        PathListRVAdapter.InnerOnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            when (val temp = viewModel.pageData.value.pathItems[position]) {
                is FileItem -> {

                }
                is FolderItem -> {
                    // set new clicked folder isSelected
                    viewModel.pageData.value.pathItems[position] = temp.copy(
                        contentId = pathListContentIdCreator.incrementAndGet(),
                        isSelected = true
                    )
                    // set original selected folder  not selected
                    when (val original =
                        viewModel.pageData.value.pathItems.getOrNull(adapter.selectedPathIndex)) {
                        is FolderItem -> {
                            viewModel.pageData.value.pathItems[adapter.selectedPathIndex] =
                                original.copy(
                                    contentId = pathListContentIdCreator.incrementAndGet(),
                                    isSelected = false
                                )
                        }
                        else -> {}
                    }
                    adapter.submitList(viewModel.pageData.value.pathItems.toMutableList())
                }
            }
            adapter.selectedPathIndex = position
            clickListener?.onItemClick(view, position)
        }

        override fun onItemLongClick(view: View, position: Int) {
            clickListener?.onItemLongClick(view, position)
        }
    }
    private var adapter: PathListRVAdapter = PathListRVAdapter().also { adapter ->
        adapter.setOnItemClickListener(innerOnItemClickListener)
        adapter.selectedPathIndex = viewModel.pageData.value.selectedIndex
    }
    private val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

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
        initVariable()
        initRVList()
        initObserver()
    }

    fun setDependency(
        pageData: PageData,
        clickListener: PathListRVAdapter.OnItemClickListener
    ) {
        simpleLog("setDependency-$pageData")
        this.parentPath = pageData.path
        this.clickListener = clickListener
        viewModel.setDependency(pageData)
    }

    private fun initVariable() {
        binding = PathListContainerBinding.inflate(LayoutInflater.from(context), this, true)
        pathListContentIdCreator.set(0)
    }

    private fun initRVList() {
        simpleLog("initRVList")
        binding.root.let { rv ->
            rv.layoutManager = LinearLayoutManager(context)
            rv.adapter = adapter
        }
    }

    private fun initObserver() {
        mainScope.launch {
            viewModel.pageData.collect {
                adapter.submitList(it.pathItems.toMutableList())
            }
        }
    }

}