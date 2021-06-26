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
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.databinding.LoginFragmentBinding
import com.keerjain.crownstailor.viewmodels.LoginViewModel
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sign_in_button -> {
                val username = binding.editTextUsername
                val password = binding.editTextPassword

                val isNotEmpty = nonEmptyList(
                    username,
                    password,
                ) { view, msg ->
                    view.error = msg
                }

                if (isNotEmpty) {
                    val isEmail = Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()

                    val tailor: TailorCredentials = if (isEmail) {
                        TailorCredentials(
                            username = null,
                            password = password.text.toString(),
                            email = username.text.toString()
                        )
                    } else {
                        TailorCredentials(
                            username = username.text.toString(),
                            password = password.text.toString(),
                            email = null
                        )
                    }

                    val isSuccess = viewModel.signIn(tailor)

                    if (isSuccess) {
                        Toast.makeText(activity, "Welcome, ${username.text.toString()}!", Toast.LENGTH_SHORT)
                            .show()
                        val toHome = LoginFragmentDirections.actionLoginFragmentToMainActivity()
                        v.findNavController().navigate(toHome)
                        activity?.finishAfterTransition()
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