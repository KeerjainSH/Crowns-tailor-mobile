package com.keerjain.crownstailor.views.register

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.databinding.FragmentRegisterChooseProductsBinding
import com.keerjain.crownstailor.utils.DataDummy
import com.keerjain.crownstailor.utils.DataMapper
import com.keerjain.crownstailor.viewmodels.RegisterViewModel
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import com.keerjain.crownstailor.views.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class RegisterChooseProductsFragment : Fragment() {

    private var _binding: FragmentRegisterChooseProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewAdapter: ProductAdapter
    private lateinit var currentActivity: LoginActivity
    private val viewModel by inject<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterChooseProductsBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            viewAdapter = ProductAdapter()
            binding.rvProductListCheckboxes.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = viewAdapter
                setHasFixedSize(true)
            }
        }

        binding.btnContinueToBankAccount.setOnClickListener {
            validateData()
        }

        currentActivity = activity as LoginActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        lifecycleScope.launchWhenCreated {
            viewModel.getProductList().collectLatest { list ->
                viewAdapter.setProductList(list)

                withContext(Dispatchers.Main) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        showLoading(false)
                    }, 500)
                }
            }
        }
        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun validateData() {
        val list = viewAdapter.getCheckedProductList()

        if (list.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.list_empty_error),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            lifecycleScope.launch(Dispatchers.Default) {
                continueToBankAccount(list)
            }
        }
    }

    private suspend fun continueToBankAccount(list: List<Product>) {
        val args: RegisterChooseProductsFragmentArgs by navArgs()
        val data = args.registrationData

        data.listIdBaju = DataMapper.mapProductListToProductIdList(list)
        val toNextStep =
            RegisterChooseProductsFragmentDirections.actionRegisterChooseProductsFragmentToRegisterBankAccountsFragment(
                data
            )

        withContext(Dispatchers.Main) {
            view?.findNavController()?.navigate(toNextStep)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.selectProductLoading.visibility = View.VISIBLE
            binding.rvProductListCheckboxes.visibility = View.GONE
        } else {
            binding.rvProductListCheckboxes.visibility = View.VISIBLE
            binding.selectProductLoading.visibility = View.GONE
        }
    }
}