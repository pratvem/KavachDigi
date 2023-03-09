package com.paras.kavach

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils.SimpleStringSplitter
import androidx.appcompat.app.AppCompatActivity
import com.paras.kavach.databinding.ActivityMainBinding
import com.paras.kavach.ui.welCome.WelcomeActivity
import com.paras.kavach.utils.services.BrowserFraudService


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, WelcomeActivity::class.java))
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//
//        binding.button.setOnClickListener {
//            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
//            startActivity(intent)
//        }
//        doInit()
    }

    @SuppressLint("SetTextI18n")
    private fun doInit() {
        val bSet: Boolean = isAccessibilitySettingsOn(this)
        binding.apply {
            if (bSet) {
                button.isEnabled = false
                textView.text = "Permission Granted"
            } else {
                button.isEnabled = true
                textView.text = "Permission Pending"
            }
        }
    }


    private fun isAccessibilitySettingsOn(mContext: Context): Boolean {
        var accessibilityEnabled = 0
        val service = packageName + "/" + BrowserFraudService::class.java.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                mContext.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        val mStringColonSplitter = SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue: String = Settings.Secure.getString(
                mContext.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            mStringColonSplitter.setString(settingValue)
            while (mStringColonSplitter.hasNext()) {
                val accessibilityService = mStringColonSplitter.next()
                if (accessibilityService.equals(service, ignoreCase = true)) {
                    return true
                }
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()

//        binding.apply {
//            textView3.text = strLogs
//            textView3.movementMethod = ScrollingMovementMethod()
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//            openOverlaySettings()
//        }
    }

    private fun openOverlaySettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

    companion object {
        private var strLogs: String = ""

        fun onBrowserRec(str: String) {
            strLogs += str + "\r\n\r\n"
        }
    }

}