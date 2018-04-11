package com.myride.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.dreader.extensions.threadToAndroid
import com.myride.repository.EntryDatabase
import com.myride.repository.entities.EntryTable
import io.reactivex.Observable

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var entries: LiveData<List<EntryTable>>? = null

    fun loadEntries() {
        entries = dao.listAll()
    }

    fun add(entry: EntryTable) {
        Observable
                .fromCallable { dao.add(entry) }
                .threadToAndroid()
                .subscribe()
    }

    private val dao by lazy { database.getEntryDao() }
    private val database by lazy { EntryDatabase.getInstance(application.applicationContext) }
}