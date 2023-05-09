package com.example.filelist.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.example.filelist.utils.SortingMode
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

        setupToolBar()
        setupFileListAdapter()
        setupFileListRecycleView()
        setupFileListObserver()

        if (fileListViewModel.fileList.value.isEmpty()) {
            fetchAllFiles(SortingMode.NAME)
        }
    }

    private fun setupToolBar() {
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    private fun fetchAllFiles(sortingMode: SortingMode) {
        fileListViewModel.fetchAllFiles(sortingMode)
    }

    private fun daggerInjection(context: Context) {
        val fileListComponent: FileListComponent =
            DaggerFileListComponent.factory().create(
                (context.applicationContext as FileListDepsProvider).fileListDepsProvider()
            )
        fileListComponent.injectFileListFragment(this)
    }

    private fun setupFileListAdapter() {
        fileListAdapter = FileListAdapter(emptyList())
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
                    fileListAdapter?.updateList(fileListState)
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


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_scan -> {
                fetchAllFiles(SortingMode.NAME)
                true
            }
            R.id.sort_by_name -> {
                fileListViewModel.sortList(SortingMode.NAME)
                true
            }
            R.id.sort_by_date -> {
                fileListViewModel.sortList(SortingMode.DATE)
                true
            }
            R.id.sort_by_size -> {
                fileListViewModel.sortList(SortingMode.SIZE_INCREASE)
                true
            }
            R.id.sort_by_size_decrease -> {
                fileListViewModel.sortList(SortingMode.SIZE_DECREASE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fileListAdapter = null
        _binding = null
    }
}
