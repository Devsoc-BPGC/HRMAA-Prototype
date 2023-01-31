package com.devsoc.hrmaa.healthConnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.devsoc.hrmaa.databinding.ActivityDateBinding

class DateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.getRecCvDa.setOnClickListener {
            val startDate = binding.startDateTietDa.text.toString()
            val endDate = binding.endDateTietDa.text.toString()
            var startDay = 1
            var startMonth = 1
            var startYear = 2023
            var endDay = 1
            var endMonth = 1
            var endYear = 2023
            if(startDate!!.isNotEmpty() && startDate.substring(0,2).isDigitsOnly() && startDate.substring(3,5).isDigitsOnly() && startDate.substring(6,10).isDigitsOnly()
                && endDate!!.isNotEmpty() && endDate.substring(0,2).isDigitsOnly() && endDate.substring(3,5).isDigitsOnly() && endDate.substring(6,10).isDigitsOnly()
                && ((startDate[2] == '/' && startDate[5] == '/' && endDate[2] == '/' && endDate[5] == '/') || (startDate[2] == '-' && startDate[5] == '-' && endDate[2] == '-' && endDate[5] == '-'))){

                startDay = startDate.substring(0,2).toInt()
                startMonth = startDate.substring(3,5).toInt()
                startYear = startDate.substring(6,10).toInt()
                endDay = endDate.substring(0,2).toInt()
                endMonth = endDate.substring(3,5).toInt()
                endYear = endDate.substring(6,10).toInt()
                if(startDay in 1..31 && startMonth in 1..12 && startYear in 1990..2023
                    && endDay in 1..31 && endMonth in 1..12 && endYear in 1990..2023
                    && ((endYear==startYear && endMonth==startMonth && endDay>startDay)||(endYear==startYear && endMonth>startMonth)|| endYear>startYear)){
                    val intent = Intent(this@DateActivity, ReadDataActivity::class.java)
                    intent.putExtra("start_date", startDate)
                    intent.putExtra("end_date", endDate)
                    startActivity(intent)

                }
                else {
                    Toast.makeText(
                        this@DateActivity,
                        "Enter a valid date!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                Toast.makeText(
                    this@DateActivity,
                    "Enter a valid date!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}