package com.example.fitnessplusapp.ui.nutrition

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessplusapp.R
import com.example.fitnessplusapp.data.local.NutritionDatabase
import com.example.fitnessplusapp.data.repository.NutritionRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class NutritionListFragment : Fragment(R.layout.fragment_nutrition_list) {

    private lateinit var adapter: FoodAdapter

    private val viewModel: NutritionViewModel by viewModels {
        val db = NutritionDatabase.getDatabase(requireContext())
        val repo = NutritionRepository(db.foodDao())
        NutritionViewModelFactory(repo)
    }

    private val dailyGoal = 2000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FoodAdapter { food ->

            viewModel.deleteFood(food)
        }

        val rv = view.findViewById<RecyclerView>(R.id.rvFood)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        val tvTodayCalories = view.findViewById<TextView>(R.id.tvTodayCalories)
        val tvTodayGoal = view.findViewById<TextView>(R.id.tvTodayGoal)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressCalories)

        tvTodayGoal.text = "Goal: $dailyGoal kcal"
        progressBar.max = dailyGoal

        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAddFood)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nutritionListFragment_to_addFoodFragment)
        }



        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.foodList.observe(viewLifecycleOwner) { list ->
                val todayList = list.filter { isToday(it.date) }
                adapter.submitList(todayList)

                val total = todayList.sumOf { it.calories }
                tvTodayCalories.text = "$total kcal"
                progressBar.progress = total.coerceAtMost(dailyGoal)
            }

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
}

