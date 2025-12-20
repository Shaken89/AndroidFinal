package com.example.fitnessplusapp.ui.progress

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fitnessplusapp.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgressFragment : Fragment(R.layout.fragment_progress) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lineChart = view.findViewById<LineChart>(R.id.lineChart)
        
        setupChart(lineChart)
        loadTestData(lineChart)
    }

    private fun setupChart(chart: LineChart) {
        chart.apply {
            description.text = "Weekly Workout Progress"
            description.textSize = 12f
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 0f
            }
            
            axisRight.isEnabled = false
            legend.isEnabled = true
        }
    }

    private fun loadTestData(chart: LineChart) {
        // Тестовые данные для графика (7 дней)
        val entries = listOf(
            Entry(1f, 150f), // Day 1: 150 calories
            Entry(2f, 200f), // Day 2: 200 calories
            Entry(3f, 180f), // Day 3: 180 calories
            Entry(4f, 250f), // Day 4: 250 calories
            Entry(5f, 220f), // Day 5: 220 calories
            Entry(6f, 300f), // Day 6: 300 calories
            Entry(7f, 280f)  // Day 7: 280 calories
        )

        val dataSet = LineDataSet(entries, "Calories Burned").apply {
            color = Color.BLUE
            lineWidth = 2.5f
            setCircleColor(Color.BLUE)
            circleRadius = 5f
            setDrawValues(true)
            valueTextSize = 10f
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        chart.data = LineData(dataSet)
        chart.invalidate() // refresh
    }
}
