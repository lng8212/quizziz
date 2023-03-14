package com.longkd.quizizz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.longkd.quizizz.databinding.FragmentLeadersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadersFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentLeadersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeadersBinding.inflate(inflater, container, false)
        return binding.root
    }
}