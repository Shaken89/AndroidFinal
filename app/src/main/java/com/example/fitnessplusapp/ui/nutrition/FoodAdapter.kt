package com.example.fitnessplusapp.ui.nutrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessplusapp.R
import com.example.fitnessplusapp.data.local.entity.FoodEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FoodAdapter(
    private val onLongClick: (FoodEntity) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var items: List<FoodEntity> = emptyList()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun submitList(list: List<FoodEntity>) {
        items = list
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMealIcon: TextView = itemView.findViewById(R.id.tvMealIcon)
        private val tvName: TextView = itemView.findViewById(R.id.tvFoodName)
        private val tvCalories: TextView = itemView.findViewById(R.id.tvFoodCalories)
        private val tvMealType: TextView = itemView.findViewById(R.id.tvMealType)

        fun bind(food: FoodEntity) {
            tvName.text = food.name
            tvCalories.text = "${food.calories} kcal"

            val time = timeFormat.format(Date(food.date))
            tvMealType.text = "${food.mealType} • $time"

            tvMealIcon.text = when (food.mealType.lowercase(Locale.getDefault())) {
                "breakfast" -> "☕️"
                "lunch" -> "🍲"
                "dinner" -> "🍽"
                "snack" -> "🍎"
                else -> "🍽"
            }

            itemView.setOnLongClickListener {
                onLongClick(food)   // по долгому клику удаляем
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
