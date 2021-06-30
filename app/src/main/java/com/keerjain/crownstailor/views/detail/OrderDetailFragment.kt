package com.keerjain.crownstailor.views.detail

import android.os.Bundle
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
import com.keerjain.crownstailor.data.entities.product.ProductListItem
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.databinding.OrderDetailFragmentBinding
import com.keerjain.crownstailor.utils.enums.Status
import com.keerjain.crownstailor.viewmodels.OrderDetailViewModel
import com.keerjain.crownstailor.views.MainActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import java.text.NumberFormat
import java.util.*

class OrderDetailFragment : Fragment() {

    private var _binding: OrderDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by inject<OrderDetailViewModel>()
    private lateinit var currentActivity: MainActivity
    private lateinit var viewAdapter: OrderDetailAdapter
    private val args: OrderDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderDetailFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        currentActivity.removeBottomBar()

        viewAdapter = OrderDetailAdapter()

        binding.rvOrderDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
            setHasFixedSize(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val transaction = args.transaction

        lifecycleScope.launchWhenCreated {
            viewModel.getTransactionDetails(transaction).collectLatest {
                bindInformation(it)
                viewAdapter.setProductList(it.productList)

                when (it.transactionStatus) {
                    Status.NEW_ORDER -> {
                        binding.btnStartWorking.visibility = View.VISIBLE
                        binding.btnSendPackage.visibility = View.GONE

                        binding.btnStartWorking.setOnClickListener {
                            startWorking()
                        }
                    }

                    Status.ON_PROGRESS -> {
                        binding.btnStartWorking.visibility = View.GONE
                        binding.btnSendPackage.visibility = View.VISIBLE

                        binding.btnSendPackage.setOnClickListener {
                            sendOrder()
                        }
                    }

                    Status.ON_DELIVERY, Status.FINISHED -> {
                        binding.btnStartWorking.visibility = View.GONE
                        binding.btnSendPackage.visibility = View.GONE
                    }
                }
            }
        }

        viewAdapter.setOnItemClickCallback(object : OrderDetailAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProductListItem) {
                showOrderInstruction(data)
            }
        })
    }

    private fun bindInformation(transaction: Transaction) {
        val currencyFormatter = NumberFormat.getCurrencyInstance()
        currencyFormatter.maximumFractionDigits = 2
        currencyFormatter.currency = Currency.getInstance("IDR")

        binding.tvInvoiceNo.text =
            resources.getString(R.string.invoice_number, transaction.trxId.toString())
        binding.tvNamaPenerima.text = transaction.shipmentDetail.receiverName
        binding.tvHpPenerima.text = transaction.shipmentDetail.receiverPhoneNumber
        binding.tvAlamatPenerima.text = transaction.shipmentDetail.receiverAddress

        binding.tvTotalAmount.text =
            currencyFormatter.format(transaction.totalAmount)

        binding.tvShipmentFee.text =
            currencyFormatter.format(transaction.shipmentDetail.shipmentFee)

        binding.tvPaymentTotal.text =
            currencyFormatter.format(transaction.totalAmount + transaction.shipmentDetail.shipmentFee)

        val status = transaction.transactionStatus
        binding.tvStatusDetail.text = resources.getString(status.getStringResources())

        when (status) {
            Status.NEW_ORDER -> {
                binding.tvStatusDetail.setBackgroundResource(
                    R.drawable.status_baru
                )
            }

            Status.ON_PROGRESS -> {
                binding.tvStatusDetail.setBackgroundResource(
                    R.drawable.status_dikerjakan
                )
            }

            Status.ON_DELIVERY -> {
                binding.tvStatusDetail.setBackgroundResource(
                    R.drawable.status_dikirim
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

    private fun startWorking() {
        Toast.makeText(requireContext(), "Started Working", Toast.LENGTH_SHORT).show()
        viewModel.startWorking()
    }

    private fun sendOrder() {
        Toast.makeText(requireContext(), "Order Sent", Toast.LENGTH_SHORT).show()
        viewModel.sendOrder()
    }
}