package com.example.test1.main.view

import MainViewModel
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.base.view.BaseActivity
import com.example.test1.base.viewmodel.ViewModelFactory
import com.example.test1.databinding.ActivityMainBinding
import com.example.test1.main.view.adapter.ManyPageRVAdapter

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
                val pathPagesRVAdapter = ManyPageRVAdapter()
                pathPagesRVAdapter.setOnItemClickListener(object :
                    ManyPageRVAdapter.OnItemClickListener {
                    override fun onItemClick(clickedPathName: String, clickFromPageNum: Int) {
                        mainViewModel.addOnePage2SpecificPage(clickedPathName, clickFromPageNum)
                        // let pages scroll to the last page
                        binding.pathPageRV.apply {
                            smoothScrollToPosition((adapter?.itemCount ?: 1))
                        }
                    }
                })
                binding.pathPageRV.adapter = pathPagesRVAdapter
            }

            fun setUpDataObserve() {
                mainViewModel.dataSet.observe(this, {
                    (binding.pathPageRV.adapter as ManyPageRVAdapter).setUpDataSet(it)
                    (binding.pathPageRV.adapter as ManyPageRVAdapter)?.notifyPathRVDataSetChanged()
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
