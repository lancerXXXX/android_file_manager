package com.example.test1.main.model

import com.example.test1.base.model.PathItem
import java.io.File

data class PathPageItem(val pathItems: MutableList<PathItem>, var selectedIndex: Int = -1) {
    companion object {
        fun parseAndAddPathListFromFileList(fileList : List<File>) : MutableList<PathItem> {
            return mutableListOf<PathItem>().apply {
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