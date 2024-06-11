package com.example.pmdm_examen

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarFormActivity : AppCompatActivity() {

    private lateinit var brandField: EditText
    private lateinit var modelField: EditText
    private lateinit var priceField: EditText
    private lateinit var licenseDateField: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: Button

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_form)

        brandField = findViewById(R.id.brandField)
        modelField = findViewById(R.id.modelField)
        priceField = findViewById(R.id.priceField)
        licenseDateField = findViewById(R.id.licenseDateField)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backToMainForm)

        db = AppDatabase.getDatabase(this)

        licenseDateField.setOnClickListener {
            showDatePickerDialog(licenseDateField)
        }

        saveButton.setOnClickListener {
            saveCar()
        }
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }

    private fun showDatePickerDialog(licenseDateField: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                licenseDateField.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun saveCar() {
        val brand = brandField.text.toString()
        val model = modelField.text.toString()
        val priceText = priceField.text.toString()
        val licenseDate = licenseDateField.text.toString()

        if (brand.isEmpty() || model.isEmpty() || priceText.isEmpty() || licenseDate.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceText.toDoubleOrNull()
        if (price == null) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
            return
        }

        val car = Car(brand = brand, model = model, price = price, licenseDate = licenseDate)

        lifecycleScope.launch(Dispatchers.IO) {
            db.carDao().insert(car)
            launch(Dispatchers.Main) {
                Toast.makeText(this@CarFormActivity, "Car saved successfully", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        }
    }
    private fun clearFields() {
        brandField.text.clear()
        modelField.text.clear()
        priceField.text.clear()
        licenseDateField.text.clear()
    }
}