package com.keerjain.crownstailor.views.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.views.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.overScrollMode = View.OVER_SCROLL_NEVER
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val currentActivity = activity as MainActivity

        val aturProdukJahit = findPreference<Preference>(resources.getString(R.string.key_atur_produk_jahit))
        val aturProfil = findPreference<Preference>(resources.getString(R.string.key_atur_profil))
        val aturRekeningBank = findPreference<Preference>(resources.getString(R.string.key_atur_rekening_bank))

        aturProdukJahit?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            currentActivity.moveSettings(0)
            true
        }

        aturProfil?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            currentActivity.moveSettings(1)
            true
        }

        aturRekeningBank?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            currentActivity.moveSettings(2)
            true
        }
    }
}