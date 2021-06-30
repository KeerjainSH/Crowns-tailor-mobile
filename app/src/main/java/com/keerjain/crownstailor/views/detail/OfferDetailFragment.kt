package com.keerjain.crownstailor.views.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.keerjain.crownstailor.R
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
    private val args: OfferDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OfferDetailFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        currentActivity.removeBottomBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycleScope.launchWhenCreated {
            val offer = args.offer

            viewModel.getOfferDetail(offer).collectLatest { offerDetail ->
                checkOfferStatus(offerDetail)
            }
        }
    }

    private fun checkOfferStatus(offerDetail: Offer) {
        when (offerDetail.offerStatus) {
            OfferStatus.NEW_OFFER -> {
                showPriceForm(offerDetail)
            }

            OfferStatus.PRICE_SENT, OfferStatus.PRICE_ACCEPTED, OfferStatus.PRICE_DECLINED -> {
                showOfferStatus(offerDetail)
            }

            OfferStatus.NEW_PRICE -> {
                showOfferForm(offerDetail)
            }
        }
    }

    private fun showPriceForm(offerDetail: Offer) {
        binding.offerPriceNotGiven.visibility = View.VISIBLE
        binding.offerPriceGiven.visibility = View.GONE

        bindBasicInformation(offerDetail)
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

        bindBasicInformation(offerDetail)
        binding.tvOfferEstimation.text = offerDetail.offerEstimation.toString()
        binding.tvOfferPrice.text = offerDetail.offerAmount.toString()
    }

    private fun showOfferForm(offerDetail: Offer) {

    }

    private fun bindBasicInformation(offerDetail: Offer) {
        binding.tvOfferProductName.text = offerDetail.productDetail.productName
        binding.imgOfferProduct.loadPicture(offerDetail.productDetail.productPhoto)
        binding.tvOfferInstruction.text = offerDetail.orderDetail.instructions

        binding.tvArm.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", offerDetail.orderDetail.armSize)
        )
        binding.tvNeck.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", offerDetail.orderDetail.neckSize)
        )
        binding.tvWaist.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", offerDetail.orderDetail.waistSize)
        )
        binding.tvHeight.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", offerDetail.orderDetail.bodyHeight)
        )
        binding.tvChest.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", offerDetail.orderDetail.chestSize)
        )
        binding.tvWeight.text = resources.getString(
            R.string.weight_format,
            String.format("%.0f", offerDetail.orderDetail.bodyWeight)
        )

        if (offerDetail.orderDetail.design != null && offerDetail.orderDetail.design != "") {
            binding.tvDesignLink.text = resources.getString(R.string.design_link)
            binding.tvDesignLink.isClickable = true
            binding.tvDesignLink.isFocusable = true
            binding.tvDesignLink.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue_color))
            binding.tvDesignLink.setOnClickListener {
                val toBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(offerDetail.orderDetail.design))
                startActivity(toBrowser)
            }
        } else {
            binding.tvDesignLink.text = resources.getString(R.string.no_specific_design)
        }
    }
}