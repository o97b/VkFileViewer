package com.example.filelist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.FileData
import com.example.filelist.databinding.FileItemBinding

internal class FileListAdapter(

): ListAdapter<FileData, FileItem>(FileListDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FileItem(FileItemBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: FileItem, position: Int) {
        val fileData = getItem(position)
        holder.bind(fileData)
    }

}

internal class FileItem(
    private val binding: FileItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(fileData: FileData) {
        binding.fileName.text = fileData.name
        binding.fileCreationDate.text = fileData.dateCreated.toString()
        binding.fileSize.text = fileData.size.toString()
    }
}

internal class FileListDiffCallback : DiffUtil.ItemCallback<FileData>() {
    override fun areItemsTheSame(oldItem: FileData, newItem: FileData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FileData, newItem: FileData): Boolean {
        return oldItem == newItem
    }

}