package com.example.test1.base.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

open class BaseActivity : ComponentActivity() {

    private val baseActivityTag = "BaseActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(baseActivityTag, javaClass.simpleName) // know which activity is currently
    }
}
