package com.paras.kavach.ui.setting

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivitySettingBinding
import com.paras.kavach.ui.accessibility.AccessibilityActivity
import com.paras.kavach.ui.privacy.PrivacySheet

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private var isAccessibility = false
    private val viewModel: SettingVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        /** Click Listeners */
        clickListeners()
    }

    override fun onResume() {
        super.onResume()
        isAccessibility = viewModel.isAccessibilitySettingsOn(this)
        if (isAccessibility) {
            openOverlaySettings()
        }
    }

    /**
     * Overlay Permission to Show Alert on Chrome
     */
    private fun openOverlaySettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Click Listeners
     */
    private fun clickListeners() {
        binding.apply {
            include.llBack.setOnClickListener {
                finish()
            }

            btnSettings.setOnClickListener {
                /** Check Accessibility */
                checkAccessibility()
            }
        }
    }

    /**
     * Check Accessibility
     */
    private fun checkAccessibility() {
        if (isAccessibility) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        } else {
            val privacySheet = PrivacySheet()
            privacySheet.show(supportFragmentManager, "")
            privacySheet.callback = {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)

//                startActivity(
//                    Intent(
//                        this@SettingActivity,
//                        AccessibilityActivity::class.java
//                    )
//                )
            }
        }
    }

}