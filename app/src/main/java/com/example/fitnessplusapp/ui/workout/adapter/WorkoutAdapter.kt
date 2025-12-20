package com.example.fitnessplusapp.ui.workout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessplusapp.R
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity

class WorkoutAdapter(
    private var workouts: List<WorkoutEntity>,
    private val onDeleteClick: (WorkoutEntity) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvWorkoutName)
        val tvCategory: TextView = view.findViewById(R.id.tvWorkoutCategory)
        val tvDuration: TextView = view.findViewById(R.id.tvWorkoutDuration)
        val tvCalories: TextView = view.findViewById(R.id.tvWorkoutCalories)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.tvName.text = workout.name
        holder.tvCategory.text = workout.category
        holder.tvDuration.text = "${workout.duration} min"
        holder.tvCalories.text = "${workout.caloriesBurned} kcal"

        holder.btnDelete.setOnClickListener {
            onDeleteClick(workout)
        }
    }

    override fun getItemCount() = workouts.size

    fun updateData(newWorkouts: List<WorkoutEntity>) {
        val diffCallback = WorkoutDiffCallback(workouts, newWorkouts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        
        workouts = newWorkouts
        diffResult.dispatchUpdatesTo(this)
    }
    
    private class WorkoutDiffCallback(
        private val oldList: List<WorkoutEntity>,
        private val newList: List<WorkoutEntity>
    ) : DiffUtil.Callback() {
        
        override fun getOldListSize() = oldList.size
        
        override fun getNewListSize() = newList.size
        
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
        
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}