package com.paras.kavach.ui.popUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paras.kavach.databinding.LayoutPermissionPopUpBinding

class PermissionDialog : DialogFragment() {
    lateinit var binding: LayoutPermissionPopUpBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutPermissionPopUpBinding.inflate(layoutInflater, container, false)
        return binding.root.rootView
    }
}