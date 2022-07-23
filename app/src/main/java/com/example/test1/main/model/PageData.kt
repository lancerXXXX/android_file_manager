package com.example.test1.main.model

import java.io.File
import java.util.concurrent.atomic.AtomicLong

private val contentIdCreator = AtomicLong()

data class PageData(val pathItems: MutableList<PathData>, var selectedIndex: Int = -1) {

    val uId: Long = contentIdCreator.incrementAndGet()

    companion object {
        fun parseAndAddPathListFromFileList(fileList : List<File>) : MutableList<PathData> {
            return mutableListOf<PathData>().apply {
                for (file in fileList) {
                    add(
                        if (file.isDirectory) FolderItem(file.name, file.path)
                        else FileItem(file.name, file.path)
                    )
                }
            }
        }
    }

}