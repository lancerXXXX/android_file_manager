import android.annotation.SuppressLint
import android.app.Application
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test1.main.model.PathItem
import com.example.test1.main.model.PathPageItem
import com.example.test1.main.model.repository.PathRepository
import com.example.test1.utils.extension.simpleLog
import java.io.File

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val dataSet: LiveData<List<PathPageItem>> get() = _dataSet
    private val _dataSet = MutableLiveData<List<PathPageItem>>()
    private var _dataSetInner = mutableListOf<PathPageItem>()
    private val _repository by lazy { PathRepository() }

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    init {
        fun mockData(): MutableList<PathPageItem> {
            val tempPathItems1 = mutableListOf<PathItem>().apply {
                add(PathItem("path1-1", ""))
                add(PathItem("path1-1", ""))
                add(PathItem("path1-3", ""))
                add(PathItem("path1-4", ""))
            }

            val tempPathItems2 = mutableListOf<PathItem>().apply {
                add(PathItem("path2-1", ""))
                add(PathItem("path2-2", ""))
                add(PathItem("path2-3", ""))
                add(PathItem("path2-4", ""))
            }

            val tempPathPageItem1 = PathPageItem(tempPathItems1)
            val tempPathPageItem2 = PathPageItem(tempPathItems2)

            val tempPathPageItems = mutableListOf<PathPageItem>().apply {
                add(tempPathPageItem1)
                add(tempPathPageItem2)
            }

            return tempPathPageItems
        }

        _dataSetInner.apply {
            clear()
            add(PathPageItem(PathPageItem.parseAndAddPathListFromFileList(initFirstPathPage())))
            _dataSet.value = this
            refreshData()
        }

    }

    private fun refreshData() {
        _dataSet.postValue(_dataSetInner)
    }

    private fun initFirstPathPage(): List<File> {
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

    fun addPathPage2WhichPage(path: String, clickFromPage: Int) {
        val parentFile = File(path)
        simpleLog("addPathPage2WhichPage - ${path ?: "path is null"}")
        val nextPagePaths = _repository.getPathListByFile(parentFile).apply {
            simpleLog(toString())
        }
        _dataSetInner.apply {
            slice(0..clickFromPage).let {
                this.clear()
                this.addAll(it)
            }
            add(PathPageItem(PathPageItem.parseAndAddPathListFromFileList(nextPagePaths)))
        }
        refreshData()
    }
}
