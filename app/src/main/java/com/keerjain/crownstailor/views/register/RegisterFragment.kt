package com.keerjain.crownstailor.views.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.RegisterFragmentBinding
import com.keerjain.crownstailor.viewmodels.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(), View.OnClickListener {
    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)

        binding.registerButton.setOnClickListener(this)
        binding.googleRegisterButton.setOnClickListener(this)
        binding.loginTextView.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_text_view -> {
                val toLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                v.findNavController().navigate(toLogin)
            }
        }
    }
}