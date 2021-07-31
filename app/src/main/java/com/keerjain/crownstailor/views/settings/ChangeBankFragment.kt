package com.keerjain.crownstailor.views.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.FragmentChangeBankBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.toEditable
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import com.keerjain.crownstailor.views.MainActivity
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class ChangeBankFragment : Fragment() {

    private var _binding: FragmentChangeBankBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity
    private val viewModel by inject<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeBankBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            binding.btnSaveBankData.setOnClickListener {
                showLoading(true)
                validateData()
            }
        }

        currentActivity = activity as MainActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        currentActivity.removeBottomBar()

        lifecycleScope.launchWhenCreated {
            viewModel.getProfile().collectLatest { profile ->
                withContext(Dispatchers.Main) {
                    binding.etBankName.text = profile.bank?.toEditable()
                    binding.etAccountHolder.text = profile.namaPemilikRekening?.toEditable()
                    binding.etAccountNumber.text = profile.noRekening?.toEditable()
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.changeBankLoading.visibility = View.VISIBLE
            binding.linearLayoutCompat.visibility = View.GONE
        } else {
            binding.linearLayoutCompat.visibility = View.VISIBLE
            binding.changeBankLoading.visibility = View.GONE
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
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getProfile().collectLatest { profile ->
                    profile.bank = binding.etBankName.text.toString()
                    profile.noRekening = binding.etAccountNumber.text.toString()
                    profile.namaPemilikRekening = binding.etAccountHolder.text.toString()

                    viewModel.updateProfile(profile).collectLatest { isSuccessful ->
                        if (isSuccessful) {
                            val toProfileFragment = ChangeBankFragmentDirections.actionChangeBankFragmentToNavigationOther()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(),
                                    resources.getString(R.string.update_success),
                                    Toast.LENGTH_SHORT
                                ).show()

                                showLoading(false)

                                view?.findNavController()?.navigate(toProfileFragment)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(),
                                    resources.getString(R.string.update_error),
                                    Toast.LENGTH_SHORT
                                ).show()

                                showLoading(false)
                            }
                        }
                    }
                }
            }
        }
    }
}