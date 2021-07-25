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
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import com.keerjain.crownstailor.views.MainActivity
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.coroutines.Dispatchers
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
                validateData()
            }
        }

        currentActivity = activity as MainActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        currentActivity.removeBottomBar()
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
                viewModel.setBankAccount()

                val toProfileFragment = ChangeBankFragmentDirections.actionChangeBankFragmentToNavigationOther()
                withContext(Dispatchers.Main) {
                    view?.findNavController()?.navigate(toProfileFragment)
                }
            }
        }
    }
}