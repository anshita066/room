package com.example.room.roomdemo

import androidx.room.*
import com.example.room.EmployeeDataClass

// @annotation_name are inbuilt annontations
// of Androidx Room

@Dao
interface RoomEmployeeDoa {

    @Query("SELECT * FROM emp_table")
    fun getAllEmployeeDetails(): List<EmployeeDataClass>

    @Insert
    fun insertEmployeeDetails(employee: EmployeeDataClass)

    @Update
    fun updateEmployeeDetails(employee: EmployeeDataClass)

    @Delete
    fun deleteEmployeeDetails(employee: EmployeeDataClass)
}