package com.keerjain.crownstailor.views.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.databinding.SettingFragmentBinding
import com.keerjain.crownstailor.utils.SessionManager
import com.keerjain.crownstailor.viewmodels.SettingViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(), View.OnClickListener {

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

        binding.btnLogout.setOnClickListener(this)

        return binding.root
    }

    private fun signOut() {
        sessionManager.logout()
        val toLoginActivity = ProfileFragmentDirections.actionNavigationOtherToLoginActivity()
        view?.findNavController()?.navigate(toLoginActivity)
        activity?.finishAfterTransition()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout -> {
                logoutAlert?.show()
            }
        }
    }
}