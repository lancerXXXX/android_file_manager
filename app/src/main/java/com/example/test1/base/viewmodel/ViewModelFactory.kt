package com.example.test1.base.viewmodel

import MainViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}