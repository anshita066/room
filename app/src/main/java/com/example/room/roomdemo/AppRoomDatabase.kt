package com.example.room.roomdemo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room.EmployeeDataClass
import com.example.room.roomdemo.RoomEmployeeDoa

// Entity means a row
@Database(entities = [EmployeeDataClass::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun employeeDao(): RoomEmployeeDoa
}
