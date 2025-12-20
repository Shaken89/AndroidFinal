package com.example.fitnessplusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fitnessplusapp.ui.workout.WorkoutListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            Log.d("MainActivity", "Setting content view")
            setContentView(R.layout.activity_main)

            if (savedInstanceState == null) {
                Log.d("MainActivity", "Loading WorkoutListFragment")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, WorkoutListFragment())
                    .commit()
            }
            
            Log.d("MainActivity", "MainActivity created successfully")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate", e)
        }
    }
}
