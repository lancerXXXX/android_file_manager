package com.example.test1.utils.extension

import android.util.Log

fun Any.simpleLog(msg: Any) {
    Log.d("_swithun-xxxx_${this::class.java.simpleName}", msg.toString())
}