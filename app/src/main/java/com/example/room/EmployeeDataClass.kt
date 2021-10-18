package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// This data class is being used for both
// SQLite and Androidx Room

@Entity(tableName = "emp_table")
data class EmployeeDataClass(
    @PrimaryKey(autoGenerate = true)
    // Using @ColumnInfo we are giving custom
    // column name of table
    @ColumnInfo(name = "emp_id")
    var id: Int = 0,
    var name: String? = null,
    var contact: String? = null,
    var address: String? = null
)
