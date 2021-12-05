package com.example.test1.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test1.main.model.PathPageItem
import com.example.test1.main.model.repository.PathRepository
import com.example.test1.utils.extension.SingleLiveEvent
import com.example.test1.utils.extension.simpleLog
import java.io.File
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import com.example.test1.utils.extension.OpenFileUtil


class MainViewModel(application: Application) : AndroidViewModel(application) {
    val dataSet: LiveData<List<PathPageItem>> get() = _dataSet
    val pathLongClickEvent: LiveData<Any> get() = _pathLongClickEvent

    private val _dataSet = MutableLiveData<List<PathPageItem>>()
    private var _dataSetInner = mutableListOf<PathPageItem>()
    private val _pathLongClickEvent = SingleLiveEvent<Any>()

    private val _repository by lazy { PathRepository() }

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    init {
        _dataSetInner.apply {
            clear()
            add(PathPageItem(PathPageItem.parseAndAddPathListFromFileList(initFirstPage())))
            _dataSet.value = this
        }
        refreshData()
    }

    private fun refreshData() {
        _dataSet.postValue(_dataSetInner)
    }

    private fun initFirstPage(): List<File> {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "the external storage is not avaliable", Toast.LENGTH_SHORT)
                .show()
            return mutableListOf()
        }
        val rootFile = Environment.getExternalStorageDirectory()
        return _repository.getPathListByFile(rootFile).apply {
            simpleLog(toString())
            sortedBy { it.name }
        }
    }

    private fun addOnePage2SpecificPage(path: String, clickFromPage: Int) {
        val parentFile = File(path)
        val nextPagePaths = _repository.getPathListByFile(parentFile)
        _dataSetInner.apply {
            slice(0..clickFromPage).let {
                this.clear()
                this.addAll(it)
            }
            add(PathPageItem(PathPageItem.parseAndAddPathListFromFileList(nextPagePaths)))
        }
        refreshData()
    }

    private fun openFileByPath(context: Context, path: String) {

        val file = File(path)

        val fileType = OpenFileUtil.MATCH_ARRAY[file.extension]

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val fileURI = Uri.fromFile(file)

        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addCategory(Intent.CATEGORY_DEFAULT)
            setDataAndType(fileURI, fileType)
        }

        if (context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "failed to open file", Toast.LENGTH_SHORT).show();
        }

    }

    fun onPathLongClicked() {
        _pathLongClickEvent.call()
    }

    fun onFolderClicked(path: String, clickFromPage: Int) {
        addOnePage2SpecificPage(path, clickFromPage)
    }

    fun onFileClicked(context: Context, path: String) {
        openFileByPath(context, path)
    }
}
