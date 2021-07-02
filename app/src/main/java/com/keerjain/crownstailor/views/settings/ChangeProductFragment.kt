package com.keerjain.crownstailor.views.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.FragmentChangeProductBinding
import com.keerjain.crownstailor.databinding.FragmentRegisterChooseProductsBinding
import com.keerjain.crownstailor.utils.DataDummy
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import com.keerjain.crownstailor.views.LoginActivity
import com.keerjain.crownstailor.views.MainActivity
import com.keerjain.crownstailor.views.register.ProductAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class ChangeProductFragment : Fragment() {

    private var _binding: FragmentChangeProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewAdapter: ProductAdapter
    private lateinit var currentActivity: MainActivity
    private val viewModel by inject<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeProductBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            viewAdapter = ProductAdapter()
            binding.rvProductListCheckboxes.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = viewAdapter
                setHasFixedSize(true)
            }
        }

        binding.btnSaveProductData.setOnClickListener {
            validateData()
        }

        currentActivity = activity as MainActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewAdapter.setProductList(DataDummy.generateProducts())
        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        currentActivity.removeBottomBar()
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
                viewModel.setProductList()

                val toProfileFragment = ChangeProductFragmentDirections.actionChangeProductFragmentToNavigationOther()
                withContext(Dispatchers.Main) {
                    view?.findNavController()?.navigate(toProfileFragment)
                }
            }
        }
    }
}