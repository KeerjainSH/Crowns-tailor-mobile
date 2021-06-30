package com.keerjain.crownstailor.views.offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.databinding.OfferFragmentBinding
import com.keerjain.crownstailor.viewmodels.OfferViewModel
import com.keerjain.crownstailor.views.MainActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment : Fragment() {

    private var _binding: OfferFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<OfferViewModel>()
    private lateinit var currentActivity: MainActivity
    private lateinit var viewAdapter: OfferAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OfferFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        viewAdapter = OfferAdapter()

        binding.rvOfferList.apply {
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
            viewModel.getOffers(1).collectLatest { list ->
                viewAdapter.setOffers(list)
            }
        }

        viewAdapter.setOnItemClickCallback(object : OfferAdapter.OnItemClickCallback {
            override fun onItemClicked(data: OfferListItem) {
                showDetail(data)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        currentActivity.showBottomBar()
    }

    private fun showDetail(data: OfferListItem) {
        val toOfferDetail = OfferFragmentDirections.actionNavigationOfferToOfferDetailFragment(data)
        view?.findNavController()?.navigate(toOfferDetail)
    }
}