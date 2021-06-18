package com.keerjain.crownstailor.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.HomeFragmentBinding
import com.keerjain.crownstailor.databinding.LoginFragmentBinding
import com.keerjain.crownstailor.utils.SessionManager
import com.keerjain.crownstailor.viewmodels.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()
    private val sessionManager by inject<SessionManager>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.signOutButton.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sign_out_button -> {
                sessionManager.logout()
                Toast.makeText(activity, "Successfully logged out.", Toast.LENGTH_SHORT).show()
                val toLoginActivity = HomeFragmentDirections.actionHomeFragmentToLoginActivity()
                v.findNavController().navigate(toLoginActivity)
                activity?.finishAfterTransition()
            }

            else -> {

            }
        }
    }

}