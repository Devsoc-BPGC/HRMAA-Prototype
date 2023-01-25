package com.devsoc.hrmaa.fitbit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.databinding.FragmentHeartRateZonesBinding
import com.devsoc.hrmaa.fitbit.adapters.HeartRateZoneAdapter
import com.devsoc.hrmaa.fitbit.dataclasses.ActivitiesHeart
import com.devsoc.hrmaa.fitbit.dataclasses.HeartRateZone

class HeartRateZonesFragment : Fragment() {
    private lateinit var binding: FragmentHeartRateZonesBinding
    private val args: HeartRateZonesFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_heart_rate_zones, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activities = args.activities
        val customZones = mutableListOf<HeartRateZone>()
        for(c in activities.value.customHeartRateZones){
            customZones.add(HeartRateZone(c.caloriesOut, c.max, c.min, c.minutes, c.name))
        }

        val zones = activities.value.heartRateZones.plus(customZones)
        val adapter = HeartRateZoneAdapter(zones)
        binding.heartRateZonesRvHrzf.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}