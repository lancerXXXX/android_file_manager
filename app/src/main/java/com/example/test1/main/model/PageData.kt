package com.example.test1.main.model

import com.example.test1.main.view.adapter.pathList.model.FileItem
import com.example.test1.main.view.adapter.pathList.model.FolderItem
import com.example.test1.main.view.adapter.pathList.model.PathData
import com.example.test1.utils.extension.simpleLog
import java.io.File
import java.util.concurrent.atomic.AtomicLong

private val pageListContentIdCreator = AtomicLong()

data class PageData(
    val path: String,
    val pathItems: MutableList<PathData>,
    var selectedIndex: Int = -1
) {

    val contentId: Long = pageListContentIdCreator.getAndIncrement().also {
        this.simpleLog("PageData: path: $path - uid: $it")
    }

    companion object {
        fun parseAndAddPathListFromFileList(fileList : List<File>) : MutableList<PathData> {
            return mutableListOf<PathData>().apply {
                for (file in fileList) {
                    add(
                        if (file.isDirectory)
                            FolderItem(0, file.name, file.path, false)
                        else
                            FileItem(0, file.name, file.path)
                    )
                }
            }
        }
    }

}