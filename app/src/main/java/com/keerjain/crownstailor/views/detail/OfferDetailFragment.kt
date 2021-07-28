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
import com.keerjain.crownstailor.data.entities.offer.OfferPrices
import com.keerjain.crownstailor.databinding.OfferDetailFragmentBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.formatToCurrency
import com.keerjain.crownstailor.utils.ExtensionFunctions.loadPicture
import com.keerjain.crownstailor.utils.enums.OfferStatus
import com.keerjain.crownstailor.viewmodels.OfferDetailViewModel
import com.keerjain.crownstailor.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                viewModel.setOffer(offerDetail)
                sizeAdapter.setSizeList(offerDetail.orderDetail)
                designAdapter.setDesignList(offerDetail.designDetail)

                designAdapter.setOnItemClickCallback(object : DesignAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DesignDetail) {
                        val toBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(data.foto))
                        startActivity(toBrowser)
                    }
                })

                binding.tvOfferProductName.text = offerDetail.productDetail.productName
                binding.tvProductDescription.text = offerDetail.productDetail.productDescription
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
        binding.offerPriceUpdated.visibility = View.GONE

        binding.btnConfirmOffer.setOnClickListener {
            showLoading(true)
            val offer = viewModel.getOffer()
            val prices = OfferPrices(
                idPesanan = offer.offerId.toInt(),
                biayaJahit = binding.etHargaJahit.text.toString().toFloat(),
                biayaMaterial = binding.etHargaMaterial.text.toString().toFloat(),
                biayaKirim = binding.etHargaKirim.text.toString().toFloat(),
                biayaJemput = binding.etHargaJemput.text.toString().toFloat(),
            )

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.setPrices(prices).collectLatest { penawaran ->
                    if (penawaran.id != null) {
                        withContext(Dispatchers.Main) {
                            binding.offerPriceNotGiven.visibility = View.GONE
                            binding.offerPriceGiven.visibility = View.VISIBLE
                            binding.offerPriceUpdated.visibility = View.GONE
                            binding.tvOfferPrice.text = penawaran.jumlahPenawaran?.toFloat()?.formatToCurrency()
                            binding.tvOfferStatus.text = resources.getString(offerDetail.offerStatus.getStringResources())
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.price_sent),
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.error_input_price),
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showOfferStatus(offerDetail: Offer) {
        binding.offerPriceNotGiven.visibility = View.GONE
        binding.offerPriceGiven.visibility = View.VISIBLE
        binding.offerPriceUpdated.visibility = View.GONE
        binding.tvOfferPrice.text = offerDetail.offerAmount?.formatToCurrency()
        binding.tvOfferStatus.text = resources.getString(offerDetail.offerStatus.getStringResources())
    }

    private fun showOfferForm(offerDetail: Offer) {
        binding.offerPriceNotGiven.visibility = View.GONE
        binding.offerPriceGiven.visibility = View.GONE
        binding.offerPriceUpdated.visibility = View.VISIBLE
        binding.tvOfferNewPrice.text = offerDetail.offerAmount?.formatToCurrency()

        binding.btnConfirmNewOffer.setOnClickListener {
            showLoading(true)

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.acceptOffer().collectLatest { isOkay ->
                    if (isOkay) {
                        withContext(Dispatchers.Main) {
                            binding.offerPriceNotGiven.visibility = View.GONE
                            binding.offerPriceGiven.visibility = View.VISIBLE
                            binding.offerPriceUpdated.visibility = View.GONE
                            binding.tvOfferPrice.text = offerDetail.offerAmount?.formatToCurrency()
                            binding.tvOfferStatus.text = resources.getString(offerDetail.offerStatus.getStringResources())
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.offer_accepted),
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.error_input_price),
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                    }
                }
            }
        }

        binding.btnDeclineNewOffer.setOnClickListener {
            showLoading(true)

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.declineOffer().collectLatest { isOkay ->
                    if (isOkay) {
                        withContext(Dispatchers.Main) {
                            binding.offerPriceNotGiven.visibility = View.GONE
                            binding.offerPriceGiven.visibility = View.VISIBLE
                            binding.offerPriceUpdated.visibility = View.GONE
                            binding.tvOfferPrice.text = offerDetail.offerAmount?.formatToCurrency()
                            binding.tvOfferStatus.text = resources.getString(offerDetail.offerStatus.getStringResources())
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.offer_accepted),
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.error_input_price),
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }
}