package com.devsoc.hrmaa.fitbit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.fitbit.dataclasses.ActivitiesHeart
import com.devsoc.hrmaa.fitbit.dataclasses.HeartRateSeries
import com.devsoc.hrmaa.fitbit.dataclasses.HeartRateZone

class HeartRateAdapter(val heartRateSeries: List<ActivitiesHeart>): RecyclerView.Adapter<HeartRateAdapter.HeartRateViewHolder>() {
    var onItemClick : ((ActivitiesHeart) -> Unit)? = null

    inner class HeartRateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeartRateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.heart_rate_log, parent, false)
        return HeartRateViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeartRateViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.date_tv_hrl).text = heartRateSeries[position].dateTime
            findViewById<TextView>(R.id.heart_rate_tv_hrl).text = "${heartRateSeries[position].value.restingHeartRate} BPM"

            setOnClickListener {
                onItemClick?.invoke(heartRateSeries[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return heartRateSeries.size
    }

}