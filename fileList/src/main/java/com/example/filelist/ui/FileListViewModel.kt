package com.example.filelist.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.model.FileData
import com.example.core.model.FileUi
import com.example.database.domain.DataBaseRepository
import com.example.filelist.utils.FileExtensionUtil
import com.example.filelist.utils.SortingMode
import com.example.filemanager.domain.FileManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FileListViewModel(
    private val fileManagerRepository: FileManagerRepository,
): ViewModel() {

    private val _fileList: MutableStateFlow<List<FileUi>> = MutableStateFlow(emptyList())
    val fileList: StateFlow<List<FileUi>> = _fileList.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isScanning: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var fetchingJob: Job? = null
    private var updatingJob: Job? = null
    private var fetchingModifiedFilesJob: Job? = null

    fun fetchAllFiles(sortingMode: SortingMode) {
        if (fetchingJob?.isActive == true) return

        Log.d("@@@", "1")

        fetchingJob = viewModelScope.launch(Dispatchers.Default) {
            _isLoading.value = true
            val list = fileManagerRepository
                .fetchAllFiles()
                .sortedWith(getComparatorForSortingMode(sortingMode))
            Log.d("@@@", "2")
            _fileList.value = list
            _isLoading.value = false

            updateFileHashes()
        }

    }

    private fun updateFileHashes() {
        if (updatingJob?.isActive == true) return

        updatingJob = viewModelScope.launch(Dispatchers.Default) {
            fetchingJob?.let {
                it.join()
                val currentFiles = fileList.value
                fileManagerRepository.updateHashes(currentFiles)
            }
        }
    }

    fun fetchModifiedFiles() {
        if (fetchingModifiedFilesJob?.isActive == true) return

        fetchingModifiedFilesJob = viewModelScope.launch(Dispatchers.Default) {
            fetchingJob?.let {
                it.join()
                _isLoading.value = true
                _fileList.value =
                    fileManagerRepository.getModifiedFileUi(fileList.value)
                _isLoading.value = false
            }
        }
    }

    fun sortList(sortingMode: SortingMode) {
        val list = fileList.value
        _fileList.value = list.sortedWith(getComparatorForSortingMode(sortingMode))
    }

    private fun getComparatorForSortingMode(sortingMode: SortingMode): Comparator<FileUi> {
        return when (sortingMode) {
            SortingMode.NAME -> compareBy { it.name }
            SortingMode.DATE -> compareBy { it.dateCreated }
            SortingMode.SIZE_DECREASE -> compareByDescending { it.size }
            SortingMode.SIZE_INCREASE -> compareBy { it.size }
            SortingMode.EXTENSION -> compareBy { FileExtensionUtil.getExtension(it.name) }
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