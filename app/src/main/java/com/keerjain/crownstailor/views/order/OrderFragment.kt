package com.keerjain.crownstailor.views.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.databinding.OrderFragmentBinding
import com.keerjain.crownstailor.viewmodels.OrderViewModel
import com.keerjain.crownstailor.views.MainActivity
import com.keerjain.crownstailor.views.home.HomeAdapter
import com.keerjain.crownstailor.views.home.HomeFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : Fragment() {

    private var _binding: OrderFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<OrderViewModel>()
    private lateinit var currentActivity: MainActivity
    private lateinit var viewAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        viewAdapter = OrderAdapter()

        binding.rvOrderList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = viewAdapter
            setHasFixedSize(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity.setSupportActionBar(binding.topAppBar)

        lifecycleScope.launchWhenCreated {
            viewModel.getOrders(1).collectLatest { list ->
                viewAdapter.setOrdersList(list)
            }
        }

        viewAdapter.setOnItemClickCallback(object : OrderAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TransactionListItem) {
                showDetail(data)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        currentActivity.showBottomBar()
    }

    private fun showDetail(data: TransactionListItem) {
        val toOrderDetail = OrderFragmentDirections.actionNavigationOrderToOrderDetailFragment(data)
        view?.findNavController()?.navigate(toOrderDetail)
    }
}