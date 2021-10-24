package com.example.test1.main.view

import MainViewModel
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.base.view.BaseActivity
import com.example.test1.base.viewmodel.ViewModelFactory
import com.example.test1.databinding.ActivityMainBinding
import com.example.test1.main.view.adapter.PathPagesRVAdapter

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMVVM()
    }

    private fun initMVVM() {
        initView()
        initAction()
    }

    private fun initView() {

        fun setupViewModel() {

            fun setUpLayoutManager() {
                val layoutManager = LinearLayoutManager(this).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
                binding.pathPageRV.layoutManager = layoutManager
            }

            fun setUpAdapter() {
                val pathPagesRVAdapter = PathPagesRVAdapter()
                pathPagesRVAdapter.setOnItemClickListener(object :
                    PathPagesRVAdapter.OnItemClickListener {
                    override fun onItemClick(clickedPathName: String, clickFromPageNum: Int) {
                        mainViewModel.addPathPage2WhichPage(clickedPathName, clickFromPageNum)
                    }
                })
                binding.pathPageRV.adapter = pathPagesRVAdapter
            }

            fun setUpDataObserve() {
                mainViewModel.dataSet.observe(this, {
                    (binding.pathPageRV.adapter as PathPagesRVAdapter).setUpDataSet(it)
                    (binding.pathPageRV.adapter as PathPagesRVAdapter)?.notifyPathRVDataSetChanged()
                    binding.pathPageRV.adapter?.notifyDataSetChanged()
                })
            }

            mainViewModel = ViewModelProvider(this, ViewModelFactory(this.application)).get(
                MainViewModel::class.java
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
