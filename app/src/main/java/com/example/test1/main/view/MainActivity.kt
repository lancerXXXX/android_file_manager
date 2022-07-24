package com.example.test1.main.view

import com.example.test1.main.viewmodel.PageListViewModel
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.base.view.BaseActivity
import com.example.test1.base.viewmodel.ViewModelFactory
import com.example.test1.databinding.ActivityMainBinding
import com.example.test1.utils.extension.PermissionUtils
import android.widget.Toast
import android.os.Environment
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.main.view.adapter.pagelist.PageListRVAdapter
import com.example.test1.utils.extension.simpleLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pageListViewModel: PageListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PermissionUtils.isGrantExternalRW(this, 1)
        initMVVM()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                val sdCard = Environment.getExternalStorageState()
                if (sdCard == Environment.MEDIA_MOUNTED) {
                    Toast.makeText(this, "Have gotten storage permission", Toast.LENGTH_LONG).show()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "You cannot see files if you don't allow storage permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initMVVM() {
        initView()
        initAction()
    }

    private fun initView() {

        fun setupViewModel() {

            fun setUpLayoutManager() {
                binding.pathPageRV.layoutManager =
                    object : LinearLayoutManager(this) {
                        override fun smoothScrollToPosition(
                            recyclerView: RecyclerView?,
                            state: RecyclerView.State?,
                            position: Int
                        ) {
                            // fix horizontal scroll speed
                            val linearSmoothScroller =
                                object : LinearSmoothScroller(recyclerView!!.context) {

                                    private val MILLISECONDS_PER_INCH = 100f

                                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                                        return MILLISECONDS_PER_INCH / (displayMetrics?.densityDpi
                                            ?: 1)
                                    }
                                }.apply {
                                    targetPosition = position
                                }

                            startSmoothScroll(linearSmoothScroller)
                        }
                    }.apply {
                        orientation = LinearLayoutManager.HORIZONTAL
                    }
            }

            fun setUpAdapter() {
                val pathPagesRVAdapter = PageListRVAdapter()
                pathPagesRVAdapter.setOnItemClickListener(object :
                    PageListRVAdapter.OnItemClickListener {
                    override fun onFolderClick(clickedPathName: String, clickFromPageNum: Int) {
                        this.simpleLog("onFolderClick")
                        pageListViewModel.onFolderClicked(clickedPathName, clickFromPageNum)
                        this.simpleLog("adapter == null: ${binding.pathPageRV.adapter == null}")
                        binding.pathPageRV.adapter?.notifyDataSetChanged()
                    }

                    override fun onFileClick(clickedPathName: String) {
                        pageListViewModel.onFileClicked(this@MainActivity, clickedPathName)
                    }

                    override fun onFolderLongClick(clickedPathName: String, clickFromPageNum: Int) {
                        pageListViewModel.onPathLongClicked()
                    }

                    override fun onFileLongClick(clickedPathName: String) {
                        pageListViewModel.onPathLongClicked()
                    }
                })
                binding.pathPageRV.adapter = pathPagesRVAdapter
            }

            fun setUpDataObserve() {
                pageListViewModel.pathLongClickEvent.observe(this, Observer {
                    Toast.makeText(this, "path has been long clicked", Toast.LENGTH_SHORT).show()
                })

                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        pageListViewModel.pageList.collect { newFirstLevelPathList ->
                            this.simpleLog("collect")
                            binding.pathPageRV.adapter?.apply {
                                (this as PageListRVAdapter).submitList(newFirstLevelPathList)
                            }
                            // let pages scroll to the last page
                            binding.pathPageRV.apply {
                                smoothScrollToPosition((adapter?.itemCount ?: 1))
                            }
                        }
                    }
                }

            }

            pageListViewModel = ViewModelProvider(this, ViewModelFactory(this.application)).get(
                PageListViewModel::class.java
            )

            setUpLayoutManager()
            setUpAdapter()
            setUpDataObserve()
        }

        setupViewModel()
    }

    private fun initAction() {
    }


}
