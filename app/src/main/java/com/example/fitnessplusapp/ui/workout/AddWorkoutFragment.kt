package com.example.fitnessplusapp.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.example.fitnessplusapp.R
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity
import com.example.fitnessplusapp.ui.viewmodel.WorkoutViewModel

class AddWorkoutFragment : Fragment() {

    private lateinit var viewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]

        val etName = view.findViewById<TextInputEditText>(R.id.etWorkoutName)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinnerCategory)
        val etDuration = view.findViewById<TextInputEditText>(R.id.etDuration)
        val etCalories = view.findViewById<TextInputEditText>(R.id.etCalories)
        val btnSave = view.findViewById<Button>(R.id.btnSaveWorkout)

        // Настройка Spinner
        val categories = arrayOf("Cardio", "Strength", "Flexibility", "Sports")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val category = spinnerCategory.selectedItem.toString()
            val duration = etDuration.text.toString().toIntOrNull() ?: 0
            val calories = etCalories.text.toString().toIntOrNull() ?: 0

            if (name.isBlank() || duration == 0) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val workout = WorkoutEntity(
                name = name,
                category = category,
                duration = duration,
                caloriesBurned = calories
            )

            viewModel.insert(workout)
            Toast.makeText(context, "Workout added!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }
}