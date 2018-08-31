package com.myride.repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.myride.repository.entities.EntryTable

@Dao
interface EntryDao {

    @Query("SELECT * FROM entry")
    fun listAll(): LiveData<List<EntryTable>>

    @Insert
    fun add(item: EntryTable): Long
}