package com.devsoc.hrmaa.fitbit.fragments

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.databinding.FragmentFitbitRedirectBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date


class FitbitRedirectFragment : Fragment() {

    private lateinit var binding: FragmentFitbitRedirectBinding
    private val args: FitbitRedirectFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreference =  activity?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        if(args.code != "no_code_found"){
            val editor = sharedPreference?.edit()
            editor?.putString("userId",args.code)
            editor?.commit()
            editor?.apply()
        }
        val userId : String? = sharedPreference?.getString("userId", null)
        if(userId == null){
            val navAction = FitbitRedirectFragmentDirections.actionFitbitRedirectFragmentToFitbitAuthFragment()
            findNavController().navigate(navAction)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fitbit_redirect, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.LoginBvFrf.setOnClickListener {
            val navAction = FitbitRedirectFragmentDirections.actionFitbitRedirectFragmentToFitbitAuthFragment()
            view.findNavController().navigate(navAction)
        }
        val sharedPreference =  activity?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val userId : String = sharedPreference?.getString("userId", null)!!
        Log.d("Auth Code", userId)

        binding.ecgCvFrf.setOnClickListener {
            view.findNavController().navigate(R.id.action_fitbitRedirectFragment_to_fitbitDataFragment)
        }

        binding.bpmCvFrf.setOnClickListener {
            view.findNavController().navigate(R.id.action_fitbitRedirectFragment_to_heartRateFragment)
        }

    }
}