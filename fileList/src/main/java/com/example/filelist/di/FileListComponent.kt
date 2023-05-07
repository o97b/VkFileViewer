package com.example.filelist.di

import com.example.filelist.ui.FileListFragment
import com.example.filemanager.domain.FileManagerRepository
import dagger.Component
import javax.inject.Scope

@[FileListScope Component (
    dependencies = [FileListDeps::class]
)]
interface FileListComponent {

    fun injectFileListFragment(fileListFragment: FileListFragment)

    @Component.Factory
    interface FileListComponentFactory {
        fun create(
            fileListDeps: FileListDeps
        ): FileListComponent
    }
}

interface FileListDeps {
    fun fileManagerRepository(): FileManagerRepository
}

interface FileListDepsProvider {
    fun fileListDepsProvider(): FileListDeps
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FileListScope