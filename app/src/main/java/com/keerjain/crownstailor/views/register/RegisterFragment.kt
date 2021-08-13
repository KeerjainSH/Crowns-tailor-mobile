package com.keerjain.crownstailor.views.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.databinding.RegisterFragmentBinding
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import com.wajahatkarim3.easyvalidation.core.collection_ktx.validEmailList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment(), View.OnClickListener {
    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)

        binding.registerButton.setOnClickListener(this)
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
                val username = binding.editTextUsername
                val password = binding.editTextPassword
                val email = binding.editTextEmail

                lifecycleScope.launchWhenCreated {
                    val isNotEmpty = nonEmptyList(
                        username,
                        password,
                        email
                    ) { view, msg ->
                        view.error = msg
                    }

                    val isEmailFormatted = validEmailList(
                        email
                    ) { view, msg ->
                        view.error = msg
                    }

                    lifecycleScope.launch(Dispatchers.Default) {
                        if (isNotEmpty && isEmailFormatted) {
                            val registrationData = RegistrationData(
                                username = username.text.toString(),
                                password = password.text.toString(),
                                email = email.text.toString()
                            )

                            val toChooseProducts =
                                RegisterFragmentDirections.actionRegisterFragmentToRegisterDetailTailorFragment(
                                    registrationData
                                )

                            withContext(Dispatchers.Main) {
                                v.findNavController().navigate(toChooseProducts)
                            }
                        }
                    }
                }
            }
        }
    }
}