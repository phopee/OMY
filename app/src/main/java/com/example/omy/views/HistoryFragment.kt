package com.example.omy.views

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.omy.R
import com.example.omy.databinding.HistoryFragmentBinding
import com.example.omy.entities.ExchangeRate
import com.example.omy.models.HistoricalRatesViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate


private lateinit var binding : HistoryFragmentBinding
class HistoryFragment : Fragment() {

    private val viewModel: HistoricalRatesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.history_fragment, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        binding.view = this
        binding.viewModel = viewModel

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.ratesList.observe(viewLifecycleOwner, Observer {
            if(it.values.isNotEmpty()){
                setLineChartData()

            }
        })
    }


    fun setLineChartData() {

        val lineDataSet = viewModel.getChartDataForCurrency("RON")
        lineDataSet.color = Color.rgb(0, 155, 0)
        val data = LineData(lineDataSet)
        lineDataSet.color = resources.getColor(R.color.black)
        lineDataSet.lineWidth = 2f
        lineDataSet.circleRadius = 5f
        lineDataSet.circleColors = listOf(resources.getColor(R.color.black))
        lineDataSet.setDrawFilled(true)
        lineDataSet.valueTextSize = 12F
        lineDataSet.fillColor = resources.getColor(R.color.pumpkin)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER;
        binding.chartRON.data = data
        binding.chartRON.setBackgroundColor(resources.getColor(R.color.white))
        binding.chartRON.animateXY(2000, 2000, Easing.EaseInCubic)

    }
}








