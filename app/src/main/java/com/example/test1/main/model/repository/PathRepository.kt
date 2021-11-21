package com.example.test1.main.model.repository

import android.util.Log
import java.io.File


class PathRepository {

    fun getPathListByFile(file: File): List<File> {

        val result = mutableListOf<File>()
        file.listFiles()?.let {
            result.addAll(it)
        } ?: let {
            Log.d("swithun-xxxx", "nulllllllllll")
        }

        return result
    }

}