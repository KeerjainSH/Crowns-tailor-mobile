package com.keerjain.crownstailor.views.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.keerjain.crownstailor.databinding.OrderFragmentBinding
import com.keerjain.crownstailor.viewmodels.OrderViewModel
import com.keerjain.crownstailor.views.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : Fragment() {

    private var _binding: OrderFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<OrderViewModel>()
    private lateinit var currentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        currentActivity.setSupportActionBar(binding.topAppBar)

        return binding.root
    }
}