package com.keerjain.crownstailor.views.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.databinding.FragmentRegisterBankAccountsBinding
import com.keerjain.crownstailor.views.LoginActivity
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterBankAccountsFragment : Fragment() {

    private var _binding: FragmentRegisterBankAccountsBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: LoginActivity
    private var continueAlert: AlertDialog.Builder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBankAccountsBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            binding.btnFinishRegistration.setOnClickListener {
                validateData()
            }
        }

        currentActivity = activity as LoginActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        continueAlert = activity?.let {
            AlertDialog.Builder(it).setIcon(R.drawable.ic_register_complete)
                .setTitle(resources.getString(R.string.register_header))
                .setMessage(resources.getString(R.string.register_confirm))
                .setPositiveButton(resources.getString(R.string.logout_yes)) { _, _ ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        prepareData()
                    }
                }.setNegativeButton(resources.getString(R.string.logout_no), null)
        }
    }

    private fun validateData() {
        val isValidated = nonEmptyList(
            binding.etAccountNumber,
            binding.etBankName,
            binding.etAccountHolder,
        ) { view, msg ->
            view.error = msg
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.input_error),
                Toast.LENGTH_SHORT
            ).show()
        }

        if (isValidated) {
            continueAlert?.show()
        }
    }

    private suspend fun prepareData() {
        val args: RegisterBankAccountsFragmentArgs by navArgs()
        val data = args.registrationData

        data.noRekening = binding.etAccountNumber.text.toString()
        data.bank = binding.etBankName.text.toString()
        data.namaPemilikRekening = binding.etAccountHolder.text.toString()

        completeRegistration(data)
    }

    private suspend fun completeRegistration(data: RegistrationData) {
        val toSendData = RegisterBankAccountsFragmentDirections.actionRegisterBankAccountsFragmentToPostRegisterFragment(data)

        withContext(Dispatchers.Main) {
            view?.findNavController()?.navigate(toSendData)
        }
    }
}