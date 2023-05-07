package com.example.filelist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.model.FileData
import com.example.filemanager.domain.FileManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileListViewModel(
    private val fileManagerRepository: FileManagerRepository
): ViewModel() {

    private val _fileList: MutableStateFlow<List<FileData>> = MutableStateFlow(emptyList())
    val fileList: StateFlow<List<FileData>> = _fileList.asStateFlow()

    fun fetchAllFiles() {
        viewModelScope.launch(Dispatchers.Default) {
            _fileList.value = fileManagerRepository.fetchAllFiles()
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