package com.example.filelist.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.model.FileUi
import com.example.filelist.R
import com.example.filelist.databinding.FileItemBinding
import com.example.filelist.utils.DateFormatter
import com.example.filelist.utils.FileNameFormatter
import com.example.filelist.utils.SizeFormatter

internal class FileListAdapter(
    private var fileList: List<FileUi>
) : RecyclerView.Adapter<FileItem>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newFileList: List<FileUi>) {
        fileList = newFileList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FileItem(FileItemBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: FileItem, position: Int) {
        val fileData = fileList[position]
        holder.bind(fileData)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }
}

internal class FileItem(
    private val binding: FileItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(fileUi: FileUi) {
        with(binding) {
            fileName.text = FileNameFormatter.format(fileUi.name)
            fileCreationDate.text = DateFormatter.format(fileUi.dateCreated)
            fileSize.text = SizeFormatter.format(fileUi.size)

            loadIcon(fileUi)
        }
    }

    private fun loadIcon(fileUi: FileUi) {
        val requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)

        Glide.with(binding.root)
            .load(fileUi.path)
            .apply(requestOptions)
            .into(binding.fileIcon)
    }
}
