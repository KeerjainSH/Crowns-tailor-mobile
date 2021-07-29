package com.keerjain.crownstailor.views.order

import android.app.SearchManager
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.databinding.OrderFragmentBinding
import com.keerjain.crownstailor.utils.enums.Status
import com.keerjain.crownstailor.viewmodels.OrderViewModel
import com.keerjain.crownstailor.views.MainActivity
import com.keerjain.crownstailor.views.home.HomeAdapter
import com.keerjain.crownstailor.views.home.HomeFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
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

        binding.filterSemua.setOnClickListener {
            onRadioButtonClicked(it)
        }

        binding.filterPesananTerbayar.setOnClickListener {
            onRadioButtonClicked(it)
        }

        binding.filterSelesai.setOnClickListener {
            onRadioButtonClicked(it)
        }

        val searchManager =
            (activity as MainActivity).getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.svSearchOrder

        searchView.setSearchableInfo(searchManager.getSearchableInfo((activity as MainActivity).componentName))
        searchView.setIconifiedByDefault(false)
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewAdapter.filter.filter(newText)
                if (viewAdapter.itemCount == 0) {
                    showEmptyListAnim(true)
                } else {
                    showEmptyListAnim(false)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
        })

        searchView.setOnCloseListener {
            Log.d("Closed", "Closed")
            true
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity.setSupportActionBar(binding.topAppBar)
        showListLoading(true)

        val searchTextView = binding.svSearchOrder.findViewById(R.id.search_src_text) as TextView
        searchTextView.typeface = ResourcesCompat.getFont(requireContext(), R.font.lato)
        searchTextView.textSize = 14f

        lifecycleScope.launchWhenCreated {
            viewModel.getOrders().collectLatest { list ->
                viewAdapter.setOrdersList(list)

                withContext(Dispatchers.Main) {
                    showListLoading(false)

                    if (list.isNullOrEmpty()) {
                        showEmptyListAnim(true)
                    } else {
                        showEmptyListAnim(false)
                    }
                }
            }
        }

        viewAdapter.setOnItemClickCallback(object : OrderAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TransactionListItem) {
                showDetail(data)
            }
        })
    }

    private fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.id) {
                R.id.filter_semua -> {
                    if (checked) {
                        viewAdapter.filterWithStatus(null)
                        Log.d("FilterButton", "ALL")
                    }
                }

                R.id.filter_pesanan_terbayar -> {
                    if (checked) {
                        viewAdapter.filterWithStatus(Status.PAID_ORDER)
                        Log.d("FilterButton", "PAID ORDER")
                    }
                }

                R.id.filter_selesai -> {
                    if (checked) {
                        viewAdapter.filterWithStatus(Status.FINISHED)
                        Log.d("FilterButton", "FINISHED")
                    }
                }
            }

            if (viewAdapter.itemCount == 0) {
                showEmptyListAnim(true)
            } else {
                showEmptyListAnim(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        currentActivity.showBottomBar()
        binding.filterSemua.isChecked = true
    }

    private fun showDetail(data: TransactionListItem) {
        val toOrderDetail = OrderFragmentDirections.actionNavigationOrderToOrderDetailFragment(data)
        view?.findNavController()?.navigate(toOrderDetail)
    }

    private fun showListLoading(state: Boolean) {
        if (state) {
            binding.shimmerOrder.visibility = View.VISIBLE
            binding.rvOrderList.visibility = View.GONE
        } else {
            binding.rvOrderList.visibility = View.VISIBLE
            binding.shimmerOrder.visibility = View.GONE
        }
    }

    private fun showEmptyListAnim(state: Boolean) {
        if (state) {
            binding.animOrderEmpty.visibility = View.VISIBLE
            binding.orderEmptyList.visibility = View.VISIBLE
            binding.rvOrderList.visibility = View.GONE
            binding.shimmerOrder.visibility = View.GONE
        } else {
            binding.rvOrderList.visibility = View.VISIBLE
            binding.orderEmptyList.visibility = View.GONE
            binding.animOrderEmpty.visibility = View.GONE
            binding.shimmerOrder.visibility = View.GONE
        }
    }
}