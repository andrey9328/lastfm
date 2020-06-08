package com.technorely.lastfm.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.technorely.lastfm.BuildConfig
import com.technorely.lastfm.R
import com.technorely.lastfm.data.shared.EThemeType
import com.technorely.lastfm.data.shared.SharedSettings
import com.technorely.lastfm.databinding.DialogSettingsBinding
import org.koin.android.ext.android.inject

class SettingsDialog: DialogFragment() {

    private var binding: DialogSettingsBinding? = null

    private val shared: SharedSettings by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.settings))
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
        builder.setPositiveButton(getString(R.string.ok)) { _, _ -> applySettings(); dismiss() }
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_settings, null)
        builder.setView(view)
        binding = DataBindingUtil.bind(view)
        return builder.create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (shared.getThemeType() == EThemeType.AUTO_THEME)
            binding?.isDevTheme = BuildConfig.DEBUG
        else
            binding?.isDevTheme= shared.getThemeType() == EThemeType.DEV_THEME
        binding?.isDevMode = BuildConfig.DEBUG
        binding?.isOfflineMode = shared.isOfflineMode()
    }

    private fun applySettings() {

    }

    companion object {
        private val TAG = SettingsDialog::class.simpleName
        const val APPLY_SETTING = 456

        fun showDialog(target: Fragment, fm: FragmentManager) {
            val dialog = SettingsDialog()
            dialog.isCancelable = false
            dialog.setTargetFragment(target)
            dialog.show(fm, TAG)
        }
    }
}