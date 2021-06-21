package com.keerjain.crownstailor.views.offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.viewmodels.OfferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment : Fragment() {

    private val viewModel by viewModel<OfferViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.offer_fragment, container, false)
    }
}