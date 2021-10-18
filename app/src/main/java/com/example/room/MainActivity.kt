package com.example.room


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.room.roomdemo.RoomDatabaseBuilder

import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // insertDataInDBUsingSQLite()
        val addDummySqliteDemoBtn:Button = findViewById(R.id.addDummyRoomDataBtn)
        val sqliteDemoBtn:Button = findViewById(R.id.sqliteDemoBtn)
        sqliteDemoBtn.setOnClickListener {
            startActivity(
                Intent(this, DatabaseActivity::class.java).putExtra(
                    MainActivity.BUTTON_CLICKED_KEY,
                    MainActivity.ROOM_DEMO_BTN
                )
            )
        }

        addDummySqliteDemoBtn.setOnClickListener {
            insertDataInDBUsingRoom()
            Toast.makeText(this, "Data added successfully", Toast.LENGTH_LONG).show()
        }
    }

    private fun insertDataInDBUsingRoom() {
        val database = RoomDatabaseBuilder.getInstance(this)

        Executors.newSingleThreadExecutor().execute {
            database.employeeDao().insertEmployeeDetails(
                EmployeeDataClass(
                    name = "Anshita",
                    contact = "4343225678",
                    address = "Jaipur"
                )
            )
            database.employeeDao().insertEmployeeDetails(
                EmployeeDataClass(
                    name = "Kavita",
                    contact = "6784561890",
                    address = "HP"
                )
            )
            database.employeeDao().insertEmployeeDetails(
                EmployeeDataClass(
                    name = "Ritika",
                    contact = "8950154321",
                    address = "Delhi"
                )
            )

        }
    }
    companion object {
        const val BUTTON_CLICKED_KEY = "BUTTON_CLICKED"
        const val ROOM_DEMO_BTN = "ROOM_DEMO_BTN"
    }
}