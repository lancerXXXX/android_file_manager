package com.example.test1.main.model

import java.io.File

data class PathPageItem(val pathItems: MutableList<PathItem>) {
    companion object {
        fun parseAndAddPathListFromFileList(fileList : List<File>) : MutableList<PathItem> {
            return mutableListOf<PathItem>().apply {
                for (file in fileList) {
                    add(PathItem(file.name, file.path))
                }
            }
        }
    }

}