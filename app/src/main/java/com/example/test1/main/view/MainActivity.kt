package com.example.test1.main.view

import com.example.test1.main.viewmodel.MainViewModel
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.base.view.BaseActivity
import com.example.test1.base.viewmodel.ViewModelFactory
import com.example.test1.databinding.ActivityMainBinding
import com.example.test1.main.view.adapter.ManyPageRVAdapter
import com.example.test1.utils.extension.PermissionUtils
import android.widget.Toast
import android.os.Environment
import android.content.pm.PackageManager

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

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
                binding.pathPageRV.layoutManager = LinearLayoutManager(this).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }

            fun setUpAdapter() {
                val pathPagesRVAdapter = ManyPageRVAdapter()
                pathPagesRVAdapter.setOnItemClickListener(object :
                    ManyPageRVAdapter.OnItemClickListener {
                    override fun onFolderClick(clickedPathName: String, clickFromPageNum: Int) {
                        mainViewModel.addOnePage2SpecificPage(clickedPathName, clickFromPageNum)
                        // let pages scroll to the last page
                        binding.pathPageRV.apply {
                            smoothScrollToPosition((adapter?.itemCount ?: 1))
                        }
                    }

                    override fun onFileClick(clickedPathName: String) {
                    }
                })
                binding.pathPageRV.adapter = pathPagesRVAdapter
            }

            fun setUpDataObserve() {
                mainViewModel.dataSet.observe(this, {
                    binding.pathPageRV.adapter?.apply {
                        (this as ManyPageRVAdapter).setUpDataSet(it)
                        notifyDataSetChanged()
                    }
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
