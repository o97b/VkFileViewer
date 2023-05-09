package com.example.filelist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.model.FileData
import com.example.filelist.utils.SortingMode
import com.example.filemanager.domain.FileManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FileListViewModel(
    private val fileManagerRepository: FileManagerRepository
): ViewModel() {

    private val _fileList: MutableStateFlow<List<FileData>> = MutableStateFlow(emptyList())
    val fileList: StateFlow<List<FileData>> = _fileList.asStateFlow()

    fun fetchAllFiles(sortingMode: SortingMode) {
        viewModelScope.launch(Dispatchers.Default) {
            viewModelScope.launch(Dispatchers.Default) {
                val list = fileManagerRepository
                    .fetchAllFiles()
                    .sortedWith(getComparatorForSortingMode(sortingMode))

                _fileList.value = list
            }
        }
    }

    fun sortList(sortingMode: SortingMode) {
        val list = fileList.value
        _fileList.value = list.sortedWith(getComparatorForSortingMode(sortingMode))
    }

    private fun getComparatorForSortingMode(sortingMode: SortingMode): Comparator<FileData> {
        return when (sortingMode) {
            SortingMode.NAME -> compareBy { it.name }
            SortingMode.DATE -> compareBy { it.dateCreated }
            SortingMode.SIZE_DECREASE -> compareByDescending { it.size }
            SortingMode.SIZE_INCREASE -> compareBy { it.size }
            SortingMode.EXTENSION -> compareBy { getFileExtension(it.name) }
        }
    }

    private fun getFileExtension(fileName: String): String {
        val extensionStart = fileName.lastIndexOf('.')
        return if (extensionStart == -1 || extensionStart == fileName.length - 1) {
            ""
        } else {
            fileName.substring(extensionStart)
        }
    }

}

class FileListViewModelFactory @Inject constructor(
    private val fileManagerRepository: FileManagerRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass) {
            FileListViewModel::class.java -> {
                FileListViewModel(fileManagerRepository)
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}