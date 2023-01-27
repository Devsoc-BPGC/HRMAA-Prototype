package com.devsoc.hrmaa.healthConnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.health.connect.client.records.HeartRateRecord
import androidx.recyclerview.widget.RecyclerView
import com.devsoc.hrmaa.R
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class HeartRateSeriesAdapter(val heartRateSeries: List<HeartRateRecord.Sample>): RecyclerView.Adapter<HeartRateSeriesAdapter.HeartRateSeriesViewHolder>() {

    inner class HeartRateSeriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeartRateSeriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.heart_rate_log_hc, parent, false)
        return HeartRateSeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeartRateSeriesViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.datetime_tv_hrlhc).text = SimpleDateFormat("dd-MM-yyyy      hh:mm:ss a").format(Date.from(heartRateSeries[position].time))
            findViewById<TextView>(R.id.bpm_tv_hrlhc).text = "${heartRateSeries[position].beatsPerMinute} BPM"
        }
    }

    override fun getItemCount(): Int {
        return heartRateSeries.size
    }

}