package com.example.pmdm_examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var db: AppDatabase
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db = AppDatabase.getDatabase(this)

        backButton = findViewById(R.id.backToMainList)
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
        loadCars()
    }

    private fun loadCars() {
        lifecycleScope.launch(Dispatchers.IO) {
            val carList = db.carDao().getAll()
            withContext(Dispatchers.Main) {
                recyclerView.adapter = CarAdapter(carList)
            }
        }
    }
}
