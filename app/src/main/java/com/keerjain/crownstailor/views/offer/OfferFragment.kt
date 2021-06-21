package com.keerjain.crownstailor.views.offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.HomeFragmentBinding
import com.keerjain.crownstailor.databinding.OfferFragmentBinding
import com.keerjain.crownstailor.viewmodels.OfferViewModel
import com.keerjain.crownstailor.views.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment : Fragment() {

    private var _binding: OfferFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<OfferViewModel>()
    private lateinit var currentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OfferFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        currentActivity.setSupportActionBar(binding.topAppBar)

        return binding.root
    }
}