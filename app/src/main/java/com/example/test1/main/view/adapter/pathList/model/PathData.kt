package com.example.test1.main.view.adapter.pathList.model

sealed class PathData(
    open val contentId: Long,
    open val pathName: String,
    open val fullPath: String
) {

}