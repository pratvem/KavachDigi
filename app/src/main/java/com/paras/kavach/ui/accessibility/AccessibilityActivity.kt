package com.paras.kavach.ui.accessibility

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivityAccessibilityBinding

class AccessibilityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccessibilityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_accessibility)

        /** Click Listeners */
        clickListeners()
    }

    /**
     * Click Listeners
     */
    private fun clickListeners() {
        binding.apply {
            include.llBack.setOnClickListener {
                finish()
            }

            btnSave.setOnClickListener {
                finish()
            }
        }
    }
}