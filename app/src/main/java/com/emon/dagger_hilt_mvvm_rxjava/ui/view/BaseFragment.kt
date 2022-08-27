package com.emon.dagger_hilt_mvvm_rxjava.ui.view

import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment: Fragment() {

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    fun showToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}