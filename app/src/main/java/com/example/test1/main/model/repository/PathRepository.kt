package com.example.test1.main.model.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File


class PathRepository {

    suspend fun getPathListByFile(file: File): List<File> = withContext(Dispatchers.IO){

        val result = mutableListOf<File>()
        file.listFiles()?.let {
            result.addAll(it)
        } ?: let {
            Log.d("swithun-xxxx", "nulllllllllll")
        }

        return@withContext result
    }

}