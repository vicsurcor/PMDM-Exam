package com.example.pmdm_examen

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val brandTextView: TextView = view.findViewById(R.id.brandTextView)
        val modelTextView: TextView = view.findViewById(R.id.modelTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
        val licenseDateTextView: TextView = view.findViewById(R.id.licenseDateTextView)
        val carIcon: ImageView = view.findViewById(R.id.carIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.brandTextView.text = car.brand
        holder.modelTextView.text = car.model
        holder.priceTextView.text = car.price.toString()
        holder.licenseDateTextView.text = car.licenseDate
        holder.carIcon.setImageResource(R.drawable.ic_car) // Ensure you have a drawable icon named ic_car

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CarDetailActivity::class.java).apply {
                putExtra("BRAND", car.brand)
                putExtra("MODEL", car.model)
                putExtra("PRICE", car.price.toString())
                putExtra("LICENSE_DATE", car.licenseDate)
                putExtra("CAR_ID", car.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}

