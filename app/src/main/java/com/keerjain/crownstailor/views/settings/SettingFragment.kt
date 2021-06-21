package com.keerjain.crownstailor.views.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment() {

    private val viewModel by viewModel<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.setting_fragment, container, false)
    }
}