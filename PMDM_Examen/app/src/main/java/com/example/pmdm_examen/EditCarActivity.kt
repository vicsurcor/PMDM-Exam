package com.example.pmdm_examen

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EditCarActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var carId: Int = 0

    private lateinit var editBrandField: EditText
    private lateinit var editModelField: EditText
    private lateinit var editPriceField: EditText
    private lateinit var editLicenseDateField: EditText
    private lateinit var saveChangesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)

        db = AppDatabase.getDatabase(this)

        editBrandField = findViewById(R.id.editBrandField)
        editModelField = findViewById(R.id.editModelField)
        editPriceField = findViewById(R.id.editPriceField)
        editLicenseDateField = findViewById(R.id.editLicenseDateField)
        saveChangesButton = findViewById(R.id.saveChangesButton)

        // Get data from Intent
        val brand = intent.getStringExtra("BRAND")
        val model = intent.getStringExtra("MODEL")
        val price = intent.getStringExtra("PRICE")
        val licenseDate = intent.getStringExtra("LICENSE_DATE")
        carId = intent.getIntExtra("CAR_ID", 0)

        // Set data to EditTexts
        editBrandField.setText(brand)
        editModelField.setText(model)
        editPriceField.setText(price)
        editLicenseDateField.setText(licenseDate)

        editLicenseDateField.setOnClickListener {
            showDatePickerDialog()
        }

        saveChangesButton.setOnClickListener {
            saveChanges()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                editLicenseDateField.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun saveChanges() {
        val brand = editBrandField.text.toString()
        val model = editModelField.text.toString()
        val priceText = editPriceField.text.toString()
        val licenseDate = editLicenseDateField.text.toString()

        if (brand.isEmpty() || model.isEmpty() || priceText.isEmpty() || licenseDate.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceText.toDoubleOrNull()
        if (price == null) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedCar = Car(id = carId, brand = brand, model = model, price = price, licenseDate = licenseDate)

        lifecycleScope.launch(Dispatchers.IO) {
            db.carDao().update(updatedCar)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@EditCarActivity, "Car details updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
