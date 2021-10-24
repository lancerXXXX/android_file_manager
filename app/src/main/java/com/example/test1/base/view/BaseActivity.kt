package com.example.test1.base.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

open class BaseActivity : AppCompatActivity() {

    private val baseActivityTag = "BaseActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(baseActivityTag, javaClass.simpleName) // know which activity is currently
    }
}
