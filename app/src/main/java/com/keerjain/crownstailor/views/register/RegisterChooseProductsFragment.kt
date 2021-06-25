package com.keerjain.crownstailor.views.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.FragmentRegisterChooseProductsBinding
import com.keerjain.crownstailor.databinding.FragmentRegisterDetailTailorBinding
import com.keerjain.crownstailor.utils.DataDummy
import com.keerjain.crownstailor.views.LoginActivity
import com.keerjain.crownstailor.views.MainActivity

class RegisterChooseProductsFragment : Fragment() {

    private var _binding: FragmentRegisterChooseProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewAdapter: ProductAdapter
    private lateinit var currentActivity: LoginActivity

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

        currentActivity = activity as LoginActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewAdapter.setProductList(DataDummy.generateProducts())
        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}