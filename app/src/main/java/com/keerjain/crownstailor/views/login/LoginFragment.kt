package com.keerjain.crownstailor.views.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.databinding.LoginFragmentBinding
import com.keerjain.crownstailor.viewmodels.LoginViewModel
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                setLoading(true)
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

                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.signIn(tailor).collectLatest { isSuccess ->
                            if (isSuccess) {
                                val toHome = LoginFragmentDirections.actionLoginFragmentToMainActivity()

                                withContext(Dispatchers.Main) {
                                    Toast.makeText(activity, resources.getString(R.string.login_success, username.text.toString()), Toast.LENGTH_SHORT)
                                        .show()
                                    v.findNavController().navigate(toHome)
                                    activity?.finishAfterTransition()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(activity, resources.getString(R.string.login_failed), Toast.LENGTH_SHORT)
                                        .show()
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        setLoading(false)
                                    }, 500)
                                }
                            }
                        }
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

    private fun setLoading(state: Boolean) {
        if (state) {
            binding.loadingLogin.visibility = View.VISIBLE
            binding.loginInputs.visibility = View.INVISIBLE
        } else {
            binding.loginInputs.visibility = View.VISIBLE
            binding.loadingLogin.visibility = View.GONE
        }
    }
}