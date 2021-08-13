package com.keerjain.crownstailor.views.register

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
import androidx.navigation.fragment.navArgs
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.FragmentRegisterDetailTailorBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.hideKeyboard
import com.keerjain.crownstailor.views.LoginActivity
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class RegisterDetailTailorFragment : Fragment() {

    private var _binding: FragmentRegisterDetailTailorBinding? = null
    private val binding get() = _binding!!
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateFormatter: SimpleDateFormat
    private lateinit var tvBirthDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDetailTailorBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            binding.btnContinueToProductSelection.setOnClickListener {
                validateData()
            }

            binding.etBirthDate.inputType = InputType.TYPE_NULL
            binding.etBirthDate.setOnClickListener {
                showDateDialog()
            }
            binding.etBirthDate.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        showDateDialog()
                    }
                }

            dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            tvBirthDate = binding.etBirthDate
        }

        return binding.root
    }

    private fun showDateDialog() {
        val calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            activity as LoginActivity,
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
        val args: RegisterDetailTailorFragmentArgs by navArgs()
        val data = args.registrationData
        val genderArray = resources.getStringArray(R.array.gender)

        data.nama = binding.etFullName.text.toString()
        data.alamat = binding.etAddress.text.toString()
        data.kecamatan = binding.etKecamatan.text.toString()
        data.kota = binding.etKabupaten.text.toString()
        data.provinsi = binding.etProvinsi.text.toString()
        data.kodepos = binding.etPostalCode.text.toString()
        data.noHp = binding.etPhoneNumber.text.toString()
        data.tanggalLahir = binding.etBirthDate.text.toString()
        data.jenisKelamin = if (binding.genderSpinner.selectedItem.toString() == genderArray[0]) {
            "l"
        } else {
            "p"
        }

        val toNextStep =
            RegisterDetailTailorFragmentDirections.actionRegisterDetailTailorFragmentToRegisterChooseProductsFragment(
                data
            )

        withContext(Dispatchers.Main) {
            this@RegisterDetailTailorFragment.hideKeyboard()
            view?.findNavController()?.navigate(toNextStep)
        }
    }
}