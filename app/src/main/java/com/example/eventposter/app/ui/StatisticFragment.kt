package com.example.eventposter.app.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventposter.R
import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class StatisticFragment : Fragment() {

    companion object {
        fun newInstance() = StatisticFragment()
    }

    private lateinit var viewModel: StatisticViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[StatisticViewModel::class.java]
        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSalesChart(view.findViewById(R.id.salesChart))
        setupCheckChart(view.findViewById(R.id.checkChart))
        setupGenderChart(view.findViewById(R.id.genderChart))
        setupAgeChart(view.findViewById(R.id.ageChart))
    }

    private fun setupSalesChart(chart: LineChart) {
        // Пример данных: время (часы) vs проданные билеты
        val entries = listOf(
            Entry(0f, 10f),  // 00:00
            Entry(4f, 45f),   // 04:00
            Entry(12f, 120f), // 12:00
            Entry(20f, 85f)   // 20:00
        )

        val dataSet = LineDataSet(entries, "Продажи").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            lineWidth = 2f
        }

        chart.data = LineData(dataSet)
        chart.description.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.animateY(1000)
    }

    private fun setupCheckChart(chart: LineChart) {
        val entries = listOf(
            Entry(0f, 5f),
            Entry(6f, 30f),
            Entry(12f, 100f),
            Entry(18f, 80f)
        )

        val dataSet = LineDataSet(entries, "Проверки").apply {
            color = Color.RED
            valueTextColor = Color.BLACK
            lineWidth = 2f
        }

        chart.data = LineData(dataSet)
        chart.description.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.animateY(1000)
    }

    private fun setupGenderChart(chart: PieChart) {
        val entries = listOf(
            PieEntry(65f, "Мужчины"),
            PieEntry(35f, "Женщины")
        )

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(Color.BLUE, Color.RED)
            valueTextColor = Color.WHITE
        }

        chart.data = PieData(dataSet)
        chart.description.isEnabled = false
        chart.animateY(1000)
    }

    private fun setupAgeChart(chart: LineChart) {
        val entries = listOf(
            Entry(18f, 5f),
            Entry(25f, 20f),
            Entry(30f, 35f),
            Entry(40f, 15f)
        )

        val dataSet = LineDataSet(entries, "Возрастное распределение").apply {
            color = Color.MAGENTA
            lineWidth = 2f
            setCircleColor(Color.BLACK)
            circleRadius = 5f
            valueTextColor = Color.BLACK
        }

        chart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false

            // Ось X
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                axisMinimum = 18f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return if (value == axisMinimum) "Возраст (лет)" else "${value.toInt()}"
                    }
                }
            }

            // Ось Y
            axisLeft.apply {
                granularity = 1f
                axisMinimum = 0f
            }

            axisRight.isEnabled = false
            animateY(1000)
        }
    }
}