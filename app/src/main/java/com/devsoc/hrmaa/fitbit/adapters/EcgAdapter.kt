package com.devsoc.hrmaa.fitbit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.fitbit.dataclasses.ActivitiesHeart
import com.devsoc.hrmaa.fitbit.dataclasses.EcgReading

class EcgAdapter(val ecgSeries: List<EcgReading>): RecyclerView.Adapter<EcgAdapter.EcgViewHolder>() {

    var onItemClick : ((EcgReading) -> Unit)? = null
    inner class EcgViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.heart_rate_log, parent, false)
        return EcgViewHolder(view)
    }

    override fun onBindViewHolder(holder: EcgViewHolder, position: Int) {
        val startTime = ecgSeries[position].startTime
        holder.itemView.apply {
            findViewById<TextView>(R.id.date_tv_hrl).text = startTime.substring(0,10)+" "+startTime.substring(11)
            findViewById<TextView>(R.id.heart_rate_tv_hrl).text = "${ecgSeries[position].averageHeartRate} BPM"

            setOnClickListener {
                onItemClick?.invoke(ecgSeries[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return ecgSeries.size
    }
}