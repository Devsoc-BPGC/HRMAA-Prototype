package com.devsoc.hrmaa.fitbit.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.databinding.FragmentEcgGraphBinding
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class EcgGraphFragment : Fragment() {
    private lateinit var binding: FragmentEcgGraphBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ecg_graph, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val graphView = binding.ecgGvEgf

        val voltages = intArrayOf(272,286,314,289,289,296,658,184,305,324,344,402,455,467,339,274,270,277,281,288,290,305,298,305,328,312,316,292,662,345,328,352,388,431,463,407,287,263,259,268,269,281,285,283,299,316,299,301,299,664,145,307,323,360,403,457,455,320,257,252,261,269,276,282,294,280,297,316,308,288,297,628,218,322,340,361,401,462,480,348,263,260,275,273,283,291,286,290,301,327,296,293,290,665,256,325,338,374,423,479,445,313,261
        )
        val times = intArrayOf(12800,12832,12865,12897,12930,12962,12995,13028,13060,13092,13125,13158,13190,13222,13255,13288,13321,13352,13385,13418,13451,13483,13515,13548,13581,13614,13645,13678,13711,13744,13775,13808,13841,13874,13906,13938,13971,14004,14036,14068,14101,14134,14167,14199,14231,14264,14297,14329,14361,14394,14427,14459,14492,14524,14557,14589,14622,14654,14687,14720,14752,14785,14817,14850,14882,14915,14947,14980,15012,15045,15078,15110,15142,15175,15208,15240,15272,15305,15338,15371,15403,15435,15468,15501,15533,15565,15598,15631,15664,15695,15728,15761,15794,15826,15858,15891,15924,15956,15988,16021,16054,16087,16119
        )

        val dataPoints = mutableListOf<DataPoint>()
        for(i in 0..voltages.size-1){
            dataPoints.add(DataPoint((times[i]-times[0]).toDouble(), voltages[i].toDouble()))
        }

        val series: LineGraphSeries<DataPoint> = LineGraphSeries(dataPoints.toTypedArray())

        graphView.animate()
        graphView.viewport.isScrollable = true
        graphView.viewport.isScalable = true
        graphView.viewport.setScalableY(true)
        graphView.viewport.setScrollableY(true)
        graphView.viewport.setMaxX((times[times.size-1]-times[0]).toDouble())
        series.color = R.color.black
        series.isDrawDataPoints = true
        series.dataPointsRadius = 10F
        series.setOnDataPointTapListener { series, dataPoint ->
            binding.coordsTvEgf.text = "x = ${dataPoint.x}  y = ${dataPoint.y}"
        }
        graphView.addSeries(series)
    }


}