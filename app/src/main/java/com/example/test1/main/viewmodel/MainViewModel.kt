import android.annotation.SuppressLint
import android.app.Application
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun addOnePage2SpecificPage(path: String, clickFromPage: Int) {
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
}
