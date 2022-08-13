package com.example.test1.main.view.adapter.pathList.viewmodel

import androidx.lifecycle.ViewModel
import com.example.test1.main.model.PageData
import com.example.test1.main.view.adapter.pathList.model.PathData
import com.example.test1.main.view.adapter.pathList.view.PathListContainer
import com.example.test1.utils.extension.simpleLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PathListViewModel: ViewModel() {
    val pathList: StateFlow<List<PathData>> get() = _pathList
    private val _pathList: MutableStateFlow<MutableList<PathData>> = MutableStateFlow(mutableListOf())

    private val _pageData: MutableStateFlow<PageData> = MutableStateFlow(PageData("", mutableListOf(), -1))
    val pageData = _pageData.asStateFlow()

    fun setDependency(pageData: PageData) {
        _pageData.value = pageData.copy()
    }

}