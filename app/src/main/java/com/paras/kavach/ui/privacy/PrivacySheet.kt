package com.paras.kavach.ui.privacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paras.kavach.databinding.LayoutPrivacySheetBinding

class PrivacySheet : BottomSheetDialogFragment() {
    lateinit var binding: LayoutPrivacySheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutPrivacySheetBinding.inflate(layoutInflater, container, false)
        return binding.root.rootView
    }
}