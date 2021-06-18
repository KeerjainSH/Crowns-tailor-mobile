package com.keerjain.crownstailor.views.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.UserCredentials
import com.keerjain.crownstailor.databinding.RegisterFragmentBinding
import com.keerjain.crownstailor.viewmodels.RegisterViewModel
import com.keerjain.crownstailor.views.login.LoginFragmentDirections
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

            R.id.register_button -> {
                val username = binding.editTextUsername.text.toString()
                val password = binding.editTextPassword.text.toString()
                val email = binding.editTextEmail.text.toString()

                when {
                    username != "" && password != "" && email != "" -> {

                        val user = UserCredentials(
                            username = username,
                            password = password,
                            email = email
                        )

                        val isSuccess = viewModel.register(user)

                        if (isSuccess) {
                            Toast.makeText(activity, "Welcome, $username!", Toast.LENGTH_SHORT)
                                .show()
                            val toHome = RegisterFragmentDirections.actionRegisterFragmentToMainActivity()
                            v.findNavController().navigate(toHome)
                            activity?.finishAfterTransition()
                        }
                    }

                    username == "" -> {
                        Toast.makeText(
                            activity,
                            "Kolom username tidak boleh kosong!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    email == "" -> {
                        Toast.makeText(
                            activity,
                            "Kolom email tidak boleh kosong!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        Toast.makeText(
                            activity,
                            "Kolom password tidak boleh kosong!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}