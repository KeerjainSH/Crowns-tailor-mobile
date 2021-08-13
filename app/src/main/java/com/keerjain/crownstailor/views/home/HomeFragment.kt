package com.keerjain.crownstailor.views.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.databinding.HomeFragmentBinding
import com.keerjain.crownstailor.utils.ActivityObserver
import com.keerjain.crownstailor.utils.ExtensionFunctions.showLoading
import com.keerjain.crownstailor.utils.SessionManager
import com.keerjain.crownstailor.utils.enums.Status
import com.keerjain.crownstailor.viewmodels.HomeViewModel
import com.keerjain.crownstailor.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()
    private val sessionManager by inject<SessionManager>()
    private lateinit var currentActivity: MainActivity
    private lateinit var viewAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        viewAdapter = HomeAdapter()

        binding.rvRecentOrder.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = viewAdapter
            setHasFixedSize(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPesananBaru.showLoading(true)
        binding.tvPenawaranBaru.showLoading(true)
        binding.tvTotalPesanan.showLoading(true)
        binding.tvPesananSelesai.showLoading(true)

        currentActivity.setSupportActionBar(binding.topAppBar)
        binding.tvWelcomeGreetings.text =
            resources.getString(R.string.welcome_message, sessionManager.getSessionData()?.name)
        showListLoading(true)

        if (SessionManager(requireContext()).getLoginState()) {
            lifecycleScope.launchWhenCreated {
                viewModel.getOrders().collectLatest { list ->

                    if (list.isNullOrEmpty()) {
                        withContext(Dispatchers.Main) {
                            showListLoading(false)
                            binding.noNewOrder.visibility = View.VISIBLE
                            binding.rvRecentOrder.visibility = View.GONE
                        }
                    } else {
                        viewAdapter.setOrdersList(list)

                        withContext(Dispatchers.Main) {
                            binding.noNewOrder.visibility = View.GONE
                            showListLoading(false)
                        }
                    }

                    val finished = list.filter { it.transactionStatus == Status.FINISHED }
                    val new = list.filter { it.transactionStatus == Status.PAID_ORDER }
                    val total = list.size

                    binding.tvPesananBaru.text = new.size.toString()
                    binding.tvPesananBaru.showLoading(false)

                    binding.tvPesananSelesai.text = finished.size.toString()
                    binding.tvPesananSelesai.showLoading(false)

                    binding.tvTotalPesanan.text = total.toString()
                    binding.tvTotalPesanan.showLoading(false)
                }
            }

            lifecycleScope.launchWhenCreated {
                viewModel.getOffers().collectLatest {
                    val totalOffer = it.size.toString()

                    binding.tvPenawaranBaru.text = totalOffer
                    binding.tvPenawaranBaru.showLoading(false)
                }
            }

            viewAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(data: TransactionListItem) {
                    showDetail(data)
                }
            })
        }
    }

    private fun showDetail(data: TransactionListItem) {
        val toOrderDetail = HomeFragmentDirections.actionNavigationHomeToOrderDetailFragment(data)
        view?.findNavController()?.navigate(toOrderDetail)
    }

    override fun onResume() {
        super.onResume()

        currentActivity.showBottomBar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity?.lifecycle?.addObserver(ActivityObserver {
            (activity as MainActivity).showBottomBar()
        })
    }

    private fun showListLoading(state: Boolean) {
        if (state) {
            binding.shimmerHome.visibility = View.VISIBLE
            binding.rvRecentOrder.visibility = View.GONE
        } else {
            binding.rvRecentOrder.visibility = View.VISIBLE
            binding.shimmerHome.visibility = View.GONE
        }
    }
}