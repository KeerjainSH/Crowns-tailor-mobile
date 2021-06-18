package com.keerjain.crownstailor.views.login

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
import com.keerjain.crownstailor.databinding.LoginFragmentBinding
import com.keerjain.crownstailor.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(), View.OnClickListener {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener(this)
        binding.googleSignInButton.setOnClickListener(this)
        binding.registerTextView.setOnClickListener(this)

        return binding.root
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sign_in_button -> {
                val username = binding.editTextUsername.text.toString()
                val password = binding.editTextPassword.text.toString()

                when {
                    username != "" && password != "" -> {
                        val isEmail = Patterns.EMAIL_ADDRESS.matcher(username).matches()

                        val user: UserCredentials = if (isEmail) {
                            UserCredentials(
                                username = null,
                                password = password,
                                email = username
                            )
                        } else {
                            UserCredentials(
                                username = username,
                                password = password,
                                email = null
                            )
                        }

                        val isSuccess = viewModel.signIn(user)

                        if (isSuccess) {
                            Toast.makeText(activity, "Welcome, $username!", Toast.LENGTH_SHORT)
                                .show()
                            val toHome = LoginFragmentDirections.actionLoginFragmentToMainActivity()
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

                    else -> {
                        Toast.makeText(
                            activity,
                            "Kolom password tidak boleh kosong!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            R.id.google_sign_in_button -> {

            }

            R.id.register_text_view -> {
                val toRegisterFragment =
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                v.findNavController().navigate(toRegisterFragment)
            }
        }
    }
}