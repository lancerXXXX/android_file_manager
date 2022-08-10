package com.example.test1.main.view.adapter.pathList.model

data class FileItem(
    override val contentId: Long,
    override val pathName: String,
    override val fullPath: String) : PathData(contentId, pathName, fullPath) {
}