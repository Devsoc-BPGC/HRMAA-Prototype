package com.devsoc.hrmaa.fitbit.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.databinding.FragmentFitbitRedirectBinding


class FitbitRedirectFragment : Fragment() {

    private lateinit var binding: FragmentFitbitRedirectBinding
    val args: FitbitRedirectFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences: SharedPreferences =
            activity?.getSharedPreferences("MySharedPref", MODE_PRIVATE)!!
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fitbit_redirect, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.LoginBvFrf.setOnClickListener {
            val navAction = FitbitRedirectFragmentDirections.actionFitbitRedirectFragmentToFitbitAuthFragment()
            view.findNavController().navigate(navAction)
        }
        binding.codeTvFrf.setText(args.code)
    }
}