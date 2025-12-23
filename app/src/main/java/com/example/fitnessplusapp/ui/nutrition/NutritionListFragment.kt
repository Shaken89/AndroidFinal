package com.example.fitnessplusapp.ui.nutrition

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.text.InputType
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import com.example.fitnessplusapp.R



@AndroidEntryPoint
class NutritionListFragment : Fragment(R.layout.fragment_nutrition_list) {

    private lateinit var adapter: FoodAdapter
    private val viewModel: NutritionViewModel by viewModels()

    // Для цели калорий
    private lateinit var tvTodayGoal: TextView
    private lateinit var tvEditGoal: TextView
    private lateinit var progressCalories: ProgressBar

    private val prefs by lazy {
        requireContext().getSharedPreferences("nutrition_prefs", Context.MODE_PRIVATE)
    }

    private val dailyGoal: Int
        get() = getGoalCalories()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        adapter = FoodAdapter { food -> viewModel.deleteFood(food) }
        val rv = view.findViewById<RecyclerView>(R.id.rvFood)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        // TextView и ProgressBar
        val tvTodayCalories = view.findViewById<TextView>(R.id.tvTodayCalories)
        tvTodayGoal = view.findViewById(R.id.tvTodayGoal)
        tvEditGoal = view.findViewById(R.id.tvEditGoal)
        progressCalories = view.findViewById(R.id.progressCalories)

        // FloatingActionButton
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAddFood)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nutritionListFragment_to_addFoodFragment)
        }

        // Наблюдение за списком еды
        viewModel.foodList.observe(viewLifecycleOwner) { list ->
            val todayList = list.filter { isToday(it.date) }
            adapter.submitList(todayList)

            val total = todayList.sumOf { it.calories }
            tvTodayCalories.text = "$total kcal"
            updateGoalUI(total)
        }

        // Редактирование цели
        tvEditGoal.setOnClickListener {
            showGoalDialog()
        }
    }

    private fun isToday(timestamp: Long): Boolean {
        val cal = Calendar.getInstance()
        val todayYear = cal.get(Calendar.YEAR)
        val todayDay = cal.get(Calendar.DAY_OF_YEAR)

        cal.timeInMillis = timestamp
        val itemYear = cal.get(Calendar.YEAR)
        val itemDay = cal.get(Calendar.DAY_OF_YEAR)

        return todayYear == itemYear && todayDay == itemDay
    }

    // --- Функции для работы с целью калорий ---
    private fun getGoalCalories(): Int {
        return prefs.getInt("daily_goal", 2000)
    }

    private fun saveGoalCalories(goal: Int) {
        prefs.edit().putInt("daily_goal", goal).apply()
    }

    private fun updateGoalUI(eaten: Int) {
        tvTodayGoal.text = "Goal: ${getGoalCalories()} kcal"
        progressCalories.max = getGoalCalories()
        progressCalories.progress = eaten.coerceAtMost(getGoalCalories())
    }

    private fun showGoalDialog() {
        val editText = EditText(requireContext())
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.setText(getGoalCalories().toString())

        AlertDialog.Builder(requireContext())
            .setTitle("Set daily calorie goal")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newGoal = editText.text.toString().toIntOrNull()
                if (newGoal != null && newGoal > 0) {
                    saveGoalCalories(newGoal)
                    // Обновляем UI
                    viewModel.foodList.value?.let { list ->
                        val total = list.filter { isToday(it.date) }.sumOf { it.calories }
                        updateGoalUI(total)
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}


