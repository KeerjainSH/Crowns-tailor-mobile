package com.keerjain.crownstailor.views.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.HomeFragmentBinding
import com.keerjain.crownstailor.databinding.SettingFragmentBinding
import com.keerjain.crownstailor.utils.SessionManager
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment(), View.OnClickListener {

    private var _binding: SettingFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SettingViewModel>()
    private val sessionManager by inject<SessionManager>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SettingFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id) {

        }
    }
}