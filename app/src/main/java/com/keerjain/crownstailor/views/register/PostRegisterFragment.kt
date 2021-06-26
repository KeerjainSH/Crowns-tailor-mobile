package com.keerjain.crownstailor.views.register

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.FragmentPostRegisterBinding
import com.keerjain.crownstailor.databinding.FragmentRegisterBankAccountsBinding
import com.keerjain.crownstailor.viewmodels.RegisterViewModel
import com.keerjain.crownstailor.views.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostRegisterFragment : Fragment() {

    private var _binding: FragmentPostRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegisterViewModel>()
    private lateinit var currentActivity : LoginActivity
    private val args: PostRegisterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostRegisterBinding.inflate(inflater, container, false)

        currentActivity = activity as LoginActivity
        currentActivity.enterRegistrationState()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)

        val data = args.registrationData

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.register(data).collectLatest { isRegistrationComplete ->
                if (isRegistrationComplete) {
                    val toMainActivity = PostRegisterFragmentDirections.actionPostRegisterFragmentToMainActivity()
                    withContext(Dispatchers.Main) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            Toast.makeText(activity, resources.getString(R.string.login_success, data.username.toString()), Toast.LENGTH_SHORT)
                                .show()
                            view.findNavController().navigate(toMainActivity)
                            currentActivity.finishAfterTransition()
                        }, 2000)
                    }
                } else {
                    val toLoginScreen = PostRegisterFragmentDirections.actionPostRegisterFragmentToLoginFragment()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, resources.getString(R.string.registration_error), Toast.LENGTH_SHORT)
                            .show()
                        view.findNavController().navigate(toLoginScreen)
                    }
                }
            }
        }
    }
}