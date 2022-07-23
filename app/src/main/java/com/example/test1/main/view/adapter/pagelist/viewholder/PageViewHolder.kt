package com.example.test1.main.view.adapter.pagelist.viewholder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.databinding.PathPageItemBinding
import com.example.test1.main.model.FileItem
import com.example.test1.main.model.FolderItem
import com.example.test1.main.model.PageData
import com.example.test1.main.view.adapter.pagelist.PageListRVAdapter
import com.example.test1.main.view.adapter.pathList.PathListRVAdapter

class PageViewHolder(private val binding: PathPageItemBinding) : RecyclerView.ViewHolder(binding.root) {
    
    private val context = binding.root.context
    
    fun bind(page: PageData, clickListener: PageListRVAdapter.OnItemClickListener) {
        fun setupPathListLayoutManager() {
            /** scroll to the previous position after refresh first level page */
            // get previous layout status if have
            binding.pathRV.layoutManager?.onSaveInstanceState().let { preRVStatus ->
               binding.pathRV.layoutManager = LinearLayoutManager(context).apply {
                    // set previous layout status if have
                    onRestoreInstanceState(preRVStatus)
                }
            }
        }

        fun setupPathListAdapter() {
            val whichPage = adapterPosition
            val pathRVAdapter = PathListRVAdapter().apply {
                setOnItemClickListener(object : PathListRVAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        page.pathItems.getOrNull(position)?.let { path ->
                            when (path) {
                                is FileItem -> {
                                    clickListener.onFileClick(path.filePath)
                                }
                                is FolderItem -> {
                                    clickListener.onFolderClick(path.filePath, whichPage)
                                    page.selectedIndex = position
                                }
                            }
                        }
                    }

                    override fun onItemLongClick(view: View, position: Int) {
                        page.pathItems.getOrNull(position).let { pathItem ->
                            when (pathItem) {
                                is FolderItem -> clickListener.onFolderLongClick(pathItem.filePath, whichPage)
                                is FileItem -> clickListener.onFileLongClick(pathItem.filePath)
                            }
                        }
                    }
                })
            }
            binding.pathRV.adapter = pathRVAdapter
        }

        fun setupPathListDataSet() {
            (binding.pathRV.adapter as PathListRVAdapter).apply {
                setDataSet(page.pathItems)
                selectedPathIndex = page.selectedIndex
            }
        }

        fun setupOnePage() {
            setupPathListLayoutManager()
            setupPathListAdapter()
            setupPathListDataSet()
        }

        setupOnePage()
    }
}