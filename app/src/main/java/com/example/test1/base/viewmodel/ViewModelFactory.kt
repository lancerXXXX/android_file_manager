package com.example.test1.base.viewmodel

import com.example.test1.main.viewmodel.PageListViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PageListViewModel::class.java)) {
            return PageListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}