package com.keerjain.crownstailor.views.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.detail.DesignDetail
import com.keerjain.crownstailor.data.entities.product.ProductListItem
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.databinding.OrderDetailFragmentBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.formatToCurrency
import com.keerjain.crownstailor.utils.enums.Status
import com.keerjain.crownstailor.viewmodels.OrderDetailViewModel
import com.keerjain.crownstailor.views.MainActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject

class OrderDetailFragment : Fragment() {

    private var _binding: OrderDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by inject<OrderDetailViewModel>()
    private lateinit var currentActivity: MainActivity
    private lateinit var viewAdapter: OrderDetailAdapter
    private lateinit var designViewAdapter: DesignAdapter
    private val args: OrderDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderDetailFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        currentActivity.removeBottomBar()

        viewAdapter = OrderDetailAdapter()
        designViewAdapter = DesignAdapter()

        binding.rvOrderDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
            setHasFixedSize(true)
        }

        binding.rvOrderDesign.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = designViewAdapter
            setHasFixedSize(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val transaction = args.transaction

        lifecycleScope.launchWhenCreated {
            viewModel.getTransactionDetails(transaction).collectLatest {
                bindInformation(it)
                viewAdapter.setProductList(it.orderDetail)

                if (it.designDetail.isNullOrEmpty()) {
                    binding.rvOrderDesign.visibility = View.GONE
                    binding.tvOrderNoDesign.visibility = View.VISIBLE
                } else {
                    designViewAdapter.setDesignList(it.designDetail)
                }

                showLoading(false)
            }
        }

        viewAdapter.setOnItemClickCallback(object : OrderDetailAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProductListItem) {
                showOrderInstruction(data)
            }
        })

        designViewAdapter.setOnItemClickCallback(object : DesignAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DesignDetail) {
                val toBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(data.foto))
                startActivity(toBrowser)
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.shimmerOrderDetail.visibility = View.VISIBLE
            binding.mainOrderDetailView.visibility = View.GONE
        } else {
            binding.mainOrderDetailView.visibility = View.VISIBLE
            binding.shimmerOrderDetail.visibility = View.GONE
        }
    }

    private fun bindInformation(transaction: Transaction) {
        binding.tvInvoiceNo.text =
            resources.getString(R.string.invoice_number, transaction.trxId.toString())

        if (transaction.shipmentDetail.isNullOrEmpty()) {
            binding.tvTitlePengiriman.visibility = View.GONE
            binding.tablePengiriman.visibility = View.GONE
            binding.tvTitlePenjemputan.visibility = View.GONE
            binding.tablePenjemputan.visibility = View.GONE
            binding.hrPengiriman.visibility = View.GONE
        } else {
            for (lokasi in transaction.shipmentDetail) {
                if (lokasi.type == 1) {
                    binding.tvTitlePenjemputan.visibility = View.VISIBLE
                    binding.tablePenjemputan.visibility = View.VISIBLE
                    binding.hrPengiriman.visibility = View.VISIBLE
                    binding.tvJemputNamaPenerima.text = lokasi.receiverName
                    binding.tvJemputAlamatPenerima.text = lokasi.receiverAddress
                } else if (lokasi.type == 2) {
                    binding.tvTitlePengiriman.visibility = View.VISIBLE
                    binding.tablePengiriman.visibility = View.VISIBLE
                    binding.hrPengiriman.visibility = View.VISIBLE
                    binding.tvNamaPenerima.text = lokasi.receiverName
                    binding.tvAlamatPenerima.text = lokasi.receiverAddress
                }
            }
        }

        binding.tvPaymentTotal.text =
            transaction.totalAmount?.formatToCurrency()

        val status = transaction.transactionStatus
        binding.tvStatusDetail.text = resources.getString(status.getStringResources())

        when (status) {
            Status.PAID_ORDER -> {
                binding.tvStatusDetail.setBackgroundResource(
                    R.drawable.status_baru
                )
            }

            Status.FINISHED -> {
                binding.tvStatusDetail.setBackgroundResource(
                    R.drawable.status_selesai
                )
            }
        }
    }

    private fun showOrderInstruction(productListItem: ProductListItem) {
        val toOrderInstruction =
            OrderDetailFragmentDirections.actionOrderDetailFragmentToOrderInstructionFragment(
                productListItem
            )
        view?.findNavController()?.navigate(toOrderInstruction)
    }
}