package com.example.filelist.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.model.FileData
import com.example.filelist.R
import com.example.filelist.databinding.FileListFragmentBinding
import com.example.filelist.di.DaggerFileListComponent
import com.example.filelist.di.FileListComponent
import com.example.filelist.di.FileListDepsProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileListFragment: Fragment(R.layout.file_list_fragment) {

    private var _binding: FileListFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var fileListViewModelFactory: FileListViewModelFactory

    private val fileListViewModel: FileListViewModel by viewModels{
        fileListViewModelFactory
    }

    private var fileListAdapter: FileListAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        daggerInjection(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FileListFragmentBinding.bind(view)

        setupFileListAdapter()
        setupFileListRecycleView()
        setupFileListObserver()
        fetchAllFiles()
    }

    private fun fetchAllFiles() {
        fileListViewModel.fetchAllFiles()
    }

    private fun daggerInjection(context: Context) {
        val fileListComponent: FileListComponent =
            DaggerFileListComponent.factory().create(
                (context.applicationContext as FileListDepsProvider).fileListDepsProvider()
            )
        fileListComponent.injectFileListFragment(this)
    }

    private fun setupFileListAdapter() {
        fileListAdapter = FileListAdapter()
    }

    private fun setupFileListRecycleView() {
        binding.fileListView.adapter = fileListAdapter

        val columnCount = getColumnCount()
        val gridLayoutManager = GridLayoutManager(context, columnCount)
        binding.fileListView.layoutManager = gridLayoutManager
    }

    private fun setupFileListObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                fileListViewModel.fileList.collect { fileListState ->
                    setupScanAnimation(fileListState)
                    fileListAdapter?.submitList(fileListState)
                }
            }
        }
    }

    private fun setupScanAnimation(fileListState: List<FileData>) {
        if (fileListState.isEmpty()) {
            binding.scanAnimation.apply {
                playAnimation()
                visibility = View.VISIBLE
            }
        } else {
            binding.scanAnimation.apply {
                visibility = View.GONE
                pauseAnimation()
            }
        }
    }

    private fun getColumnCount(): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val paddingSizeList =
            resources.getDimensionPixelSize(com.example.core.R.dimen.padding_size_list)
        val iconSize = resources.getDimensionPixelSize(com.example.core.R.dimen.icon_size)
        val itemWidth = (paddingSizeList * 2 + iconSize)

        return (screenWidth / itemWidth).coerceAtLeast(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fileListAdapter = null
        _binding = null
    }
}
