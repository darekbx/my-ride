package com.myride.repository.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "entry")
data class EntryTable(
        @PrimaryKey(autoGenerate = true) var id: Int? = null,
        @ColumnInfo(name = "type") var type: Int = 0,
        @ColumnInfo(name = "score") var score: Int = 0) {
}