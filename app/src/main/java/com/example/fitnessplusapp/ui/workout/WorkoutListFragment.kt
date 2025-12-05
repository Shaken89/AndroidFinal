package com.example.fitnessplusapp.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yourpackage.fitnessplus.R
import com.yourpackage.fitnessplus.ui.viewmodel.WorkoutViewModel
import com.yourpackage.fitnessplus.ui.workout.adapter.WorkoutAdapter

class WorkoutListFragment : Fragment() {

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var adapter: WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewWorkouts)
        adapter = WorkoutAdapter(emptyList()) { workout ->
            viewModel.delete(workout)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allWorkouts.observe(viewLifecycleOwner) { workouts ->
            adapter.updateData(workouts)
        }

        view.findViewById<FloatingActionButton>(R.id.fabAddWorkout).setOnClickListener {
            // Навигация к AddWorkoutFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddWorkoutFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}