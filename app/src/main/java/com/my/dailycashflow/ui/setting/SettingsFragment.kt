package com.my.dailycashflow.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.my.dailycashflow.R
import com.my.dailycashflow.util.NotificationScheduler

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var prefManager: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val notifySwitch = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notifySwitch?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean) {
                // Jika Switch diaktifkan
                Toast.makeText(context, "Notifications Enabled", Toast.LENGTH_SHORT).show()
                enableNotifications(true)
            } else {
                // Jika Switch dinonaktifkan
                Toast.makeText(context, "Notifications Disabled", Toast.LENGTH_SHORT).show()
                enableNotifications(false)
            }
            true // Return true to save the new value
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefManager = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = prefManager.edit()

    }

    private fun enableNotifications(isEnabled: Boolean) {
        if (isEnabled) {
            // Jadwalkan atau aktifkan ulang notifikasi
            NotificationScheduler.scheduleNotifications(requireContext())
        } else {
            // Batalkan semua notifikasi yang telah dijadwalkan
            NotificationScheduler.cancelNotifications(requireContext())
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

}
