package com.keerjain.crownstailor.views.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.viewmodels.OrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : Fragment() {

    private val viewModel by viewModel<OrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_fragment, container, false)
    }
}