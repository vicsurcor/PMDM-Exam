package com.example.pmdm_examen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarDetailActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var carId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)

        db = AppDatabase.getDatabase(this)

        val brandTextView: TextView = findViewById(R.id.detailBrandTextView)
        val modelTextView: TextView = findViewById(R.id.detailModelTextView)
        val priceTextView: TextView = findViewById(R.id.detailPriceTextView)
        val licenseDateTextView: TextView = findViewById(R.id.detailLicenseDateTextView)
        val deleteButton: Button = findViewById(R.id.deleteButton)
        val backButton: Button = findViewById(R.id.backButton)
        val editButton: Button = findViewById(R.id.editCarButton)

        // Get data from Intent
        val brand = intent.getStringExtra("BRAND")
        val model = intent.getStringExtra("MODEL")
        val price = intent.getStringExtra("PRICE")
        val licenseDate = intent.getStringExtra("LICENSE_DATE")
        carId = intent.getIntExtra("CAR_ID", 0)

        // Set data to TextViews
        brandTextView.text = brand
        modelTextView.text = model
        priceTextView.text = price
        licenseDateTextView.text = licenseDate

        deleteButton.setOnClickListener {
            deleteCar()
        }

        backButton.setOnClickListener {
            finish()
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditCarActivity::class.java).apply {
                putExtra("BRAND", brand)
                putExtra("MODEL", model)
                putExtra("PRICE", price)
                putExtra("LICENSE_DATE", licenseDate)
                putExtra("CAR_ID", carId)
            }
            startActivity(intent)
        }
    }

    private fun deleteCar() {
        lifecycleScope.launch(Dispatchers.IO) {
            db.carDao().deleteById(carId)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@CarDetailActivity, "Car deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

