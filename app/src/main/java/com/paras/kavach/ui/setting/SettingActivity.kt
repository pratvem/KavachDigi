package com.paras.kavach.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivitySettingBinding
import com.paras.kavach.ui.accessibility.AccessibilityActivity
import com.paras.kavach.ui.privacy.PrivacySheet

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

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

            btnSettings.setOnClickListener {
                val privacySheet = PrivacySheet()
                privacySheet.show(supportFragmentManager, "")
                privacySheet.callback = {
                    startActivity(Intent(this@SettingActivity, AccessibilityActivity::class.java))
                }
            }
        }
    }
}