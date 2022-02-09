package com.vajro.task.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vajro.task.R
import com.vajro.task.databinding.FragmentCartBinding
import com.vajro.task.databinding.FragmentProductBinding
import com.vajro.task.databinding.FragmentSplashBinding

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class CartFragment : Fragment() {
private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as ParentActivity).setActionBarView(getString(R.string.lblCartTitle),true)
    }

}