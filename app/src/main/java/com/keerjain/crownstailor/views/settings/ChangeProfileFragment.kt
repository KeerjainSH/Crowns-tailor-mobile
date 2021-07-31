package com.keerjain.crownstailor.views.settings

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.FragmentChangeProfileBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.hideKeyboard
import com.keerjain.crownstailor.utils.ExtensionFunctions.toEditable
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import com.keerjain.crownstailor.views.MainActivity
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ChangeProfileFragment : Fragment() {

    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateFormatter: SimpleDateFormat
    private lateinit var tvBirthDate: TextView
    private lateinit var currentActivity: MainActivity
    private val viewModel by viewModel<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            binding.btnSaveProfileData.setOnClickListener {
                showLoading(true)
                validateData()
            }

            binding.etBirthDate.inputType = InputType.TYPE_NULL
            binding.etBirthDate.setOnClickListener {
                showDateDialog()
            }
            binding.etBirthDate.onFocusChangeListener =
                View.OnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        showDateDialog()
                    }
                }

            dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            tvBirthDate = binding.etBirthDate
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
                binding.etAddress.text = profile.alamat?.toEditable()
                binding.etBirthDate.text = profile.tanggalLahir?.toEditable()
                binding.etFullName.text = profile.nama?.toEditable()
                binding.etKecamatan.text = profile.kecamatan?.toEditable()
                binding.etKabupaten.text = profile.kota?.toEditable()
                binding.etProvinsi.text = profile.provinsi?.toEditable()
                binding.etPostalCode.text = profile.kodepos?.toEditable()
                binding.etPhoneNumber.text = profile.noHp?.toEditable()

                if (profile.jenisKelamin != null) {
                    if (profile.jenisKelamin == "l") {
                        binding.genderSpinner.setSelection(0)
                    } else {
                        binding.genderSpinner.setSelection(1)
                    }
                }

                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.changeProfileLoading.visibility = View.VISIBLE
            binding.mainChangeDetailsView.visibility = View.GONE
        } else {
            binding.mainChangeDetailsView.visibility = View.VISIBLE
            binding.changeProfileLoading.visibility = View.GONE
        }
    }

    private fun showDateDialog() {
        val calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            activity as MainActivity,
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                tvBirthDate.text = dateFormatter.format(newDate.time).toString()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun validateData() {
        lifecycleScope.launch(Dispatchers.Default) {
            var isValidated = false
            withContext(Dispatchers.Main) {
                isValidated = nonEmptyList(
                    binding.etFullName,
                    binding.etAddress,
                    binding.etKecamatan,
                    binding.etKabupaten,
                    binding.etProvinsi,
                    binding.etPostalCode,
                    binding.etPhoneNumber,
                    binding.etBirthDate,
                ) { view, msg ->
                    view.error = msg
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.input_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (isValidated) {
                saveDataAndNext()
            }
        }
    }

    private suspend fun saveDataAndNext() {
        viewModel.getProfile().collectLatest { profile ->
            val genderArray = resources.getStringArray(R.array.gender)

            profile.nama = binding.etFullName.text.toString()
            profile.alamat = binding.etAddress.text.toString()
            profile.kecamatan = binding.etKecamatan.text.toString()
            profile.kota = binding.etKabupaten.text.toString()
            profile.provinsi = binding.etProvinsi.text.toString()
            profile.kodepos = binding.etPostalCode.text.toString()
            profile.noHp = binding.etPhoneNumber.text.toString()
            profile.tanggalLahir = binding.etBirthDate.text.toString()
            profile.jenisKelamin = if (binding.genderSpinner.selectedItem.toString() == genderArray[0]) {
                "l"
            } else {
                "p"
            }

            viewModel.updateProfile(profile).collectLatest { isSuccessful ->
                if (isSuccessful) {
                    val toProfileFragment =
                        ChangeProfileFragmentDirections.actionChangeProfileFragmentToNavigationOther()

                    withContext(Dispatchers.Main) {
                        this@ChangeProfileFragment.hideKeyboard()

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