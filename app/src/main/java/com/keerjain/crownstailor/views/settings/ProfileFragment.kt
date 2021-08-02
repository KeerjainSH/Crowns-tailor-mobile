package com.keerjain.crownstailor.views.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.SettingFragmentBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.setProfilePicture
import com.keerjain.crownstailor.utils.SessionManager
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import com.keerjain.crownstailor.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: SettingFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SettingViewModel>()
    private val sessionManager by inject<SessionManager>()
    private var logoutAlert: AlertDialog.Builder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingFragmentBinding.inflate(inflater, container, false)

        logoutAlert = activity?.let {
            AlertDialog.Builder(it).setIcon(R.drawable.ic_exit_black)
                .setTitle(resources.getString(R.string.logout_header))
                .setMessage(resources.getString(R.string.logout_confirm))
                .setPositiveButton(resources.getString(R.string.logout_yes)) { _, _ ->
                    signOut()
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.logout_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }.setNegativeButton(resources.getString(R.string.logout_no), null)
        }

        binding.btnLogout.setOnClickListener {
            logoutAlert?.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        lifecycleScope.launchWhenCreated {
            viewModel.getRating().collectLatest { rating ->
                binding.tvRating.text = rating.toString()

                withContext(Dispatchers.Main) {
                    binding.profilePicture.setProfilePicture("https://www.pngkey.com/png/full/503-5035055_a-festival-celebrating-tractors-profile-picture-placeholder-round.png")
                    binding.tvProfileName.text = sessionManager.getSessionData()?.name

                    (activity as MainActivity).goToSettings()

                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.settingLoading.visibility = View.VISIBLE
            binding.mainSettingView.visibility = View.GONE
        } else {
            binding.mainSettingView.visibility = View.VISIBLE
            binding.settingLoading.visibility = View.GONE
        }
    }

    private fun signOut() {
        sessionManager.logout()
        val toLoginActivity = ProfileFragmentDirections.actionNavigationOtherToLoginActivity()
        view?.findNavController()?.navigate(toLoginActivity)
        activity?.finishAfterTransition()
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).showBottomBar()
    }

    fun moveToSetting(menu: Int) {
        when (menu) {
            0 -> {
                val toProdukJahit = ProfileFragmentDirections.actionNavigationOtherToChangeProductFragment()
                view?.findNavController()?.navigate(toProdukJahit)
            }

            1 -> {
                val toProfile = ProfileFragmentDirections.actionNavigationOtherToChangeProfileFragment()
                view?.findNavController()?.navigate(toProfile)
            }

            2 -> {
                val toRekeningBank = ProfileFragmentDirections.actionNavigationOtherToChangeBankFragment()
                view?.findNavController()?.navigate(toRekeningBank)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).outFromSettings()
    }
}