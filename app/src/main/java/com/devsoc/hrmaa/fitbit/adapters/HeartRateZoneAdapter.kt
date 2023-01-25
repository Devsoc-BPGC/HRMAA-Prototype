package com.devsoc.hrmaa.fitbit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.fitbit.dataclasses.HeartRateZone

class HeartRateZoneAdapter(val heartRateZones: List<HeartRateZone>): RecyclerView.Adapter<HeartRateZoneAdapter.HeartRateZoneViewHolder>() {

    inner class HeartRateZoneViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeartRateZoneViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.heart_rate_zone, parent, false)
        return HeartRateZoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeartRateZoneViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.zone_name_tv).text = "Zone Name: ${heartRateZones[position].name}"
            findViewById<TextView>(R.id.max_tv).text = "Max: ${heartRateZones[position].max}"
            findViewById<TextView>(R.id.min_tv).text = "Min: ${heartRateZones[position].min}"
            findViewById<TextView>(R.id.time_tv).text = "Minutes: ${heartRateZones[position].minutes}"
        }
    }

    override fun getItemCount(): Int {
        return heartRateZones.size
    }

}