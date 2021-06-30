package com.keerjain.crownstailor.views.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.OfferDetailFragmentBinding
import com.keerjain.crownstailor.databinding.OrderDetailFragmentBinding
import com.keerjain.crownstailor.viewmodels.OrderDetailViewModel
import com.keerjain.crownstailor.views.MainActivity
import org.koin.android.ext.android.inject

class OrderDetailFragment : Fragment() {

    private var _binding: OrderDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by inject<OrderDetailViewModel>()
    private lateinit var currentActivity: MainActivity
    private val args: OrderDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderDetailFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        currentActivity.removeBottomBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}