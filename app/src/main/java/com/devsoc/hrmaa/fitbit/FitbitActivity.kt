package com.devsoc.hrmaa.fitbit
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.databinding.ActivityFitbitBinding


class FitbitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFitbitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fitbit)
        val view = binding.root
        setContentView(view)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        findNavController(R.id.fitbit_nav_graph).handleDeepLink(intent)
    }

}