package com.example.test1.main.model.repository

import android.util.Log
import com.example.test1.utils.extension.simpleLog
import java.io.File
import java.io.FilenameFilter

class PathRepository {

    fun getPathListByFile(file: File): List<File> {
        val files = file.listFiles(
//            object : FilenameFilter {
//            override fun accept(p0: File?, p1: String?): Boolean {
//                return true
//            }

//        }
    ).also {
            simpleLog(it.size)
        }
        return files.toList()
    }

}