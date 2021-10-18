package com.example.room

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.room.roomdemo.AppRoomDatabase
import com.example.room.roomdemo.RoomDatabaseBuilder
import java.util.concurrent.Executors

class CustomAdapter(
    private val context: Context,
    private val employeeData: ArrayList<EmployeeDataClass>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val databaseRoom: AppRoomDatabase = RoomDatabaseBuilder.getInstance(context)


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val empDetailsConstraintLayout: ConstraintLayout =
            view.findViewById<ConstraintLayout>(R.id.empDetailsContraintLayout)
        val empDetailsEditConstraintLayout: ConstraintLayout =
            view.findViewById<ConstraintLayout>(R.id.editEmpDetailsConstraintLayout)

        // Finding ref to show the details
        val nameTextView: TextView = view.findViewById(R.id.empNameTextView)
        val contactTextView: TextView = view.findViewById(R.id.empContactTextView)
        val addressTextView: TextView = view.findViewById(R.id.empAddressTextView)
        val editDataImgBtn: ImageButton = view.findViewById(R.id.editEmpDetailsBtn)
        val deleteDataImgBtn: ImageButton = view.findViewById<ImageButton>(R.id.deleteEmpDetailsBtn)

        // Finding ref to edit the details
        val nameEditTextView: EditText = view.findViewById(R.id.empNameEditTextView)
        val contactEditTextView: EditText = view.findViewById(R.id.empContactEditTextView)
        val addressEditTextView: TextView = view.findViewById(R.id.empAddressEditTextView)

        val updateDetailsBtn: Button = view.findViewById(R.id.updateDetailsBtn)


    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = employeeData.size

    // Replace the contents of a view (invoked by the layout manager)
    // Used to replace/update views at a specific position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val empId = employeeData[position].id
        val empName = employeeData[position].name!!
        val empContact = employeeData[position].contact!!
        val empAddress = employeeData[position].address!!

        holder.nameTextView.text = empName
        holder.contactTextView.text = empContact
        holder.addressTextView.text = empAddress

        holder.deleteDataImgBtn.setOnClickListener {
            deleteDataFromDBUsingRoom(empId, position)
        }

        holder.editDataImgBtn.setOnClickListener {
            updateUI(holder, empName, empContact, empAddress)

            holder.updateDetailsBtn.setOnClickListener {
                val updatedName = holder.nameEditTextView.text.toString()
                val updatedContact = holder.contactEditTextView.text.toString()
                val updatedAddress = holder.addressEditTextView.text.toString()

                Log.i("Data", "=== Name: $updatedName, Contact: $updatedContact, Address: $updatedAddress ===")

                updateDetailsInDB(
                    empId,
                    updatedName,
                    updatedContact,
                    updatedAddress,
                    position,
                    holder
                )
            }
        }
    }

    // this will remove data from DB
    private fun deleteDataFromDBUsingRoom(empId: Int, position: Int) {
        Executors.newSingleThreadExecutor().execute {
            databaseRoom.employeeDao().deleteEmployeeDetails(EmployeeDataClass(id = empId))
        }
        Toast.makeText(
            context,
            "Employee with Id: $empId is deleted",
            Toast.LENGTH_SHORT
        ).show()
        // update the UI
        // it will remove data from list
        // which is passed to this adapter
        deleteItem(position)
    }

    private fun updateUI(
        holder: ViewHolder,
        empName: String,
        empContact: String,
        empAddress: String
    ) {
        // Add the data to edit text
        holder.nameEditTextView.setText(empName)
        holder.contactEditTextView.setText(empContact)
        holder.addressEditTextView.setText(empAddress)

        // hide view
        holder.empDetailsConstraintLayout.visibility = View.INVISIBLE

        // Show view where user can edit details
        holder.empDetailsEditConstraintLayout.visibility = View.VISIBLE
    }

    private fun updateDetailsInDB(
        empId: Int,
        updatedName: String,
        updatedContact: String,
        updatedAddress: String,
        position: Int,
        holder: ViewHolder
    ) {
        // Update detail using Room Dao
        Executors.newSingleThreadExecutor().execute {
            databaseRoom.employeeDao().updateEmployeeDetails(
                EmployeeDataClass(
                    id = empId,
                    name = updatedName,
                    contact = updatedContact,
                    address = updatedAddress
                )
            )
        }

        // Hide view
        holder.empDetailsEditConstraintLayout.visibility = View.GONE

        // Show view where user can view details
        holder.empDetailsConstraintLayout.visibility = View.VISIBLE

        Toast.makeText(
            context,
            "Employee with Id: $empId is updated",
            Toast.LENGTH_SHORT
        ).show()

        setItem(position, empId, updatedName, updatedContact, updatedAddress)
    }

    // Update the item in list
    private fun setItem(
        position: Int,
        empID: Int,
        empName: String,
        empContact: String,
        empAddress: String
    ) {
        employeeData[position] = EmployeeDataClass(empID, empName, empContact, empAddress)
        notifyDataSetChanged()
    }

    // delete item from list
    private fun deleteItem(position: Int) {
        employeeData.removeAt(position)
        notifyDataSetChanged()
    }
}