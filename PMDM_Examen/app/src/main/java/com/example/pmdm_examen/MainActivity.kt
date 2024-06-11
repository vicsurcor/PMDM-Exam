package com.example.pmdm_examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewCarsButton: Button = findViewById(R.id.viewCarsButton)
        viewCarsButton.setOnClickListener {
            startActivity(Intent(this, CarListActivity::class.java))
        }
        val createCarButton: Button = findViewById(R.id.createCarButton)
        createCarButton.setOnClickListener {
            startActivity(Intent(this, CarFormActivity::class.java))
        }
    }
}