package com.example.filelist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.model.FileData
import com.example.filelist.R
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
        with(binding) {
            fileName.text = fileData.displayedName()
            fileCreationDate.text = fileData.displayedDate()
            fileSize.text = fileData.displayedSize()

            loadIcon(fileData)
        }
    }

    private fun loadIcon(fileData: FileData) {
        val requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)

        Glide.with(binding.root)
            .load(fileData.path)
            .apply(requestOptions)
            .into(binding.fileIcon)
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