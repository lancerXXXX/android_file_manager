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
import com.example.test1.main.model.PageData
import com.example.test1.main.model.repository.PathRepository
import com.example.test1.utils.extension.SingleLiveEvent
import com.example.test1.utils.extension.simpleLog
import java.io.File
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.lifecycle.viewModelScope
import com.example.test1.utils.extension.OpenFileUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PageListViewModel(application: Application) : AndroidViewModel(application) {

    // file path clickListener
    val pathLongClickEvent: LiveData<Any> get() = _pathLongClickEvent
    private val _pathLongClickEvent = SingleLiveEvent<Any>()

    // file paths container container
    val pageList: StateFlow<List<PageData>> get() = _pageList
    private val _pageList = MutableStateFlow<MutableList<PageData>>(mutableListOf())

    private val _repository by lazy { PathRepository() }

    private val rootFile = Environment.getExternalStorageDirectory().also {
        this.simpleLog("rootFile: ${it.path}")
    }

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    init {
        viewModelScope.launch {
            _pageList.value = mutableListOf(PageData(rootFile.path, PageData.parseAndAddPathListFromFileList(initFirstPage())))
        }
    }

    private suspend fun initFirstPage(): List<File> {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "the external storage is not avaliable", Toast.LENGTH_SHORT)
                .show()
            return mutableListOf()
        }
        return _repository.getPathListByFile(rootFile).apply {
            simpleLog(toString())
            sortedBy { it.name }
        }
    }

    private suspend fun addOnePage2SpecificPage(path: String, clickFromPage: Int) {

        this.simpleLog("try get PageData : path: $path clickFromPage: $clickFromPage")
        val parentFile = File(path)
        val nextPagePaths = _repository.getPathListByFile(parentFile)

        val pageData = _pageList.value.slice(0..clickFromPage).toMutableList().also {
            it.add(PageData(path, PageData.parseAndAddPathListFromFileList(nextPagePaths)))
        }

        this.simpleLog("got PageData")
        _pageList.value = pageData
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
        viewModelScope.launch {
            addOnePage2SpecificPage(path, clickFromPage)
        }
    }

    fun onFileClicked(context: Context, path: String) {
        openFileByPath(context, path)
    }
}
