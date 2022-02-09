package com.vajro.task.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vajro.task.R
import com.vajro.task.databinding.FragmentSplashBinding

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class SplashFragment : Fragment() {
private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            findNavController().navigate(R.id.navigate_to_productFragment)
        },2000)
    }



}