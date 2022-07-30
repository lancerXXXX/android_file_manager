package com.example.test1.main.view.adapter.pathList.viewmodel

import androidx.lifecycle.ViewModel
import com.example.test1.main.view.adapter.pathList.model.PathData
import com.example.test1.utils.extension.simpleLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PathListViewModel: ViewModel() {
    val pathList: StateFlow<List<PathData>> get() = _pathList
    private val _pathList: MutableStateFlow<MutableList<PathData>> = MutableStateFlow(mutableListOf())

    fun setDependency(pathList: MutableList<PathData>) {
        simpleLog("viewmodel setData _${_pathList.value} - ${pathList}")
        _pathList.value = pathList
    }

}