package com.keerjain.crownstailor.views.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.detail.DesignDetail
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.databinding.OfferDetailFragmentBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.loadPicture
import com.keerjain.crownstailor.utils.enums.OfferStatus
import com.keerjain.crownstailor.viewmodels.OfferDetailViewModel
import com.keerjain.crownstailor.views.MainActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferDetailFragment : Fragment() {

    private var _binding: OfferDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<OfferDetailViewModel>()
    private lateinit var currentActivity: MainActivity
    private lateinit var sizeAdapter: SizeAdapter
    private lateinit var designAdapter: DesignAdapter
    private val args: OfferDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OfferDetailFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        currentActivity.removeBottomBar()

        sizeAdapter = SizeAdapter()
        designAdapter = DesignAdapter()

        binding.rvSizeList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sizeAdapter
            setHasFixedSize(true)
        }

        binding.rvDesignList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = designAdapter
            setHasFixedSize(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycleScope.launchWhenCreated {
            val offer = args.offer

            viewModel.getOfferDetail(offer).collectLatest { offerDetail ->
                sizeAdapter.setSizeList(offerDetail.orderDetail)
                designAdapter.setDesignList(offerDetail.designDetail)

                designAdapter.setOnItemClickCallback(object : DesignAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DesignDetail) {
                        val toBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(data.foto))
                        startActivity(toBrowser)
                    }
                })

                binding.tvOfferProductName.text = offerDetail.productDetail.productName
                binding.imgOfferProduct.loadPicture(offerDetail.productDetail.productPhoto)

                checkOfferStatus(offerDetail)
            }
        }
    }

    private fun checkOfferStatus(offerDetail: Offer) {
        when (offerDetail.offerStatus) {
            OfferStatus.OFFER_NEW -> {
                showPriceForm(offerDetail)
                showLoading(false)
            }

            OfferStatus.OFFER_RESPONSE_SENT, OfferStatus.OFFER_ACCEPTED -> {
                showOfferStatus(offerDetail)
                showLoading(false)
            }

            OfferStatus.OFFER_NEW_PRICE -> {
                showOfferForm(offerDetail)
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.offerViewLoading.visibility = View.VISIBLE
            binding.mainOfferView.visibility = View.GONE
        } else {
            binding.mainOfferView.visibility = View.VISIBLE
            binding.offerViewLoading.visibility = View.GONE
        }
    }

    private fun showPriceForm(offerDetail: Offer) {
        binding.offerPriceNotGiven.visibility = View.VISIBLE
        binding.offerPriceGiven.visibility = View.GONE
        binding.btnConfirmOffer.setOnClickListener {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.price_sent),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showOfferStatus(offerDetail: Offer) {
        binding.offerPriceNotGiven.visibility = View.GONE
        binding.offerPriceGiven.visibility = View.VISIBLE
        binding.tvOfferPrice.text = offerDetail.offerAmount.toString()
    }

    private fun showOfferForm(offerDetail: Offer) {
    }
}