package com.paras.kavach.ui.privacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paras.kavach.databinding.LayoutPrivacySheetBinding

class PrivacySheet : BottomSheetDialogFragment() {

    private lateinit var binding: LayoutPrivacySheetBinding
    var callback: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutPrivacySheetBinding.inflate(layoutInflater, container, false)
        return binding.root.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Click Listeners */
        clickListeners()
    }

    /**
     * Click Listeners
     */
    private fun clickListeners() {
        binding.apply {
            ivCross.setOnClickListener {
                dismiss()
            }

            btnDisagree.setOnClickListener {
                dismiss()
            }

            btnAgree.setOnClickListener {
                callback?.invoke()
                dismiss()
            }
        }
    }

}