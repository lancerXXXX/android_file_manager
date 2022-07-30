package com.example.test1.main.view.adapter.pathList.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.databinding.PathListContainerBinding
import com.example.test1.main.view.adapter.pathList.model.PathData
import com.example.test1.main.view.adapter.pathList.view.adapter.PathListRVAdapter
import com.example.test1.main.view.adapter.pathList.viewmodel.PathListViewModel
import com.example.test1.utils.extension.simpleLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * TODO: document your custom view class.
 */
class PathListContainer : LinearLayout {
    /* ================= view =================== */
    private lateinit var binding: PathListContainerBinding
    /* ================= viewModel ============== */
    private lateinit var viewModel: PathListViewModel
    /* ================= data =================== */
    private var pathList: MutableList<PathData> = mutableListOf()
    /* ================= util =================== */
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private lateinit var adapter: PathListRVAdapter
    private var clickListener: PathListRVAdapter.OnItemClickListener? = null

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
        this.simpleLog("1. [PathListContainer] init")
    }

    fun setDependency(pathList: MutableList<PathData>, clickListener: PathListRVAdapter.OnItemClickListener) {
        simpleLog("2. [setDependency] - pathList.size: ${pathList.size}")
        this.pathList = pathList
        this.clickListener = clickListener
    }

    override fun onAttachedToWindow() {
        simpleLog("3. [onAttachedToWindow]")
        super.onAttachedToWindow()
        initRVList()
        viewModel =
            ViewModelProvider(findViewTreeViewModelStoreOwner()!!).get<PathListViewModel>()
                .also {
                    simpleLog("pathListViewMode: $it")
                }
        initObserve()
        viewModel.setDependency(pathList.toMutableList())
    }


    private fun initRVList() {
        simpleLog("4. [initRVList]")
        binding = PathListContainerBinding.inflate(LayoutInflater.from(context), this, true)
        binding.root.layoutManager = LinearLayoutManager(context)
        adapter = PathListRVAdapter().apply {
            clickListener?.let { this.setOnItemClickListener(it) }
        }
        binding.root.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun initObserve() {
        simpleLog("5. [initObserve]")
        scope?.launch {
            viewModel.pathList.collect {
                simpleLog("pathList collect it.size: ${it.size}")
                adapter.setDataSet(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

}