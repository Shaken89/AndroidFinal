package com.example.fitnessplusapp.ui.nutrition

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnessplusapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFoodFragment : Fragment(R.layout.fragment_add_food) {

    private val viewModel: NutritionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<TextInputEditText>(R.id.etFoodName)
        val etCalories = view.findViewById<TextInputEditText>(R.id.etCalories)
        val spinnerMealType = view.findViewById<Spinner>(R.id.spinnerMealType)
        val btnSave = view.findViewById<Button>(R.id.btnSaveFood)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.meal_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMealType.adapter = adapter
        }

        btnSave.setOnClickListener {
            val name = etName.text?.toString()?.trim().orEmpty()
            val calories = etCalories.text?.toString()?.toIntOrNull() ?: 0
            val mealType = spinnerMealType.selectedItem.toString()
            val date = System.currentTimeMillis()

            if (name.isNotBlank() && calories > 0) {
                viewModel.addFood(name, calories, mealType, date)
                findNavController().popBackStack()
            } else {

            }
        }
    }
}
