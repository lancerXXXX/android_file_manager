package com.example.test1.utils.extension

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson = GsonBuilder().setPrettyPrinting().create()

fun Any.simpleLog(msg: Any) {
    Log.d("swithun-xxxx_${this::class.java.simpleName}", msg.toString())
}

fun Any.toJson(): String {
    return gson.toJson(this)
}