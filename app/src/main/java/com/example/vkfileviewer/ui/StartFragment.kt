package com.example.vkfileviewer.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vkfileviewer.BuildConfig
import com.example.vkfileviewer.R

class StartFragment: Fragment(R.layout.start_fragment) {

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                navigateToFileList()
            } else {
                requestManageExternalStoragePermission()
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED) {
                navigateToFileList()
            } else {
                requestReadExternalStoragePermission()
            }
        }
    }

    private fun navigateToFileList() {
        val navController = findNavController()
        navController.navigate(R.id.fileListFragment)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun requestManageExternalStoragePermission() {
        val uri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
        startActivity(
            Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                uri
            )
        )
    }

    private fun requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_READ_EXTERNAL_STORAGE
        )
    }

    companion object {
        const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1
    }
}