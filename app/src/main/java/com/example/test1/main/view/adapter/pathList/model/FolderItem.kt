package com.example.test1.main.view.adapter.pathList.model

data class FolderItem(
    override val contentId: Long,
    override val pathName: String,
    override val fullPath: String,
    val isSelected: Boolean
) : PathData(contentId, pathName, fullPath) {

}