package com.myride.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.myride.repository.entities.EntryTable

@Database(entities = arrayOf(EntryTable::class), version = 1)
abstract class EntryDatabase : RoomDatabase() {

    abstract fun getEntryDao() : EntryDao

    companion object {

        private val DB_NAME = "entry_db"

        @Volatile
        private var INSTANCE: EntryDatabase? = null

        fun getInstance(context: Context): EntryDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context)
                            .also { database ->
                                INSTANCE = database
                            }
                }

        private fun buildDatabase(context: Context) =
                Room
                        .databaseBuilder(context.applicationContext, EntryDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()

    }
}