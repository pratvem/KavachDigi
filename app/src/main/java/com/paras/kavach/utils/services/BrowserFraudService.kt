package com.paras.kavach.utils.services

import android.accessibilityservice.AccessibilityService
import android.util.Patterns
import android.view.accessibility.AccessibilityEvent
import androidx.appcompat.app.AlertDialog
import com.paras.kavach.R
import com.paras.kavach.data.repository.ApiRepository
import com.paras.kavach.utils.getSystemFlag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class BrowserFraudService : AccessibilityService() {

    @Inject
    lateinit var apiRepository: ApiRepository
    private var browserApp = ""
    private var browserUrl = ""

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
            event.eventType == AccessibilityEvent.TYPE_WINDOWS_CHANGED ||
            event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
        ) {
            val parentNodeInfo = event.source ?: return
            val packageName = event.packageName.toString()
            var browserConfig: BrowserFraudUtils.SupportedBrowserConfig? = null
            for (supportedConfig in BrowserFraudUtils.getSupportedBrowsers()) {
                if (supportedConfig.packageName == packageName) {
                    browserConfig = supportedConfig
                }
            }

            if (browserConfig == null) {
                return
            }
            val capturedUrl = BrowserFraudUtils.captureUrl(parentNodeInfo, browserConfig)
            parentNodeInfo.recycle()
            if (capturedUrl == null) {
                return
            }

            if (packageName != browserApp) {
                browserApp = packageName
                /** Check Url */
                checkUrl(capturedUrl)
            } else {
                if (capturedUrl != browserUrl) {
                    /** Check Url */
                    checkUrl(capturedUrl)
                }
            }
        }

    }

    /**
     * Check Url
     */
    private fun checkUrl(capturedUrl: String) {
        browserUrl = capturedUrl
        if (Patterns.WEB_URL.matcher(capturedUrl).matches()) {
            var checkUrl = capturedUrl
            if (capturedUrl.contains("/")) {
                val separated = capturedUrl.split("/")
                checkUrl = separated[0]
            }

            showFraudAlert()
            //checkWebsiteSecureOrNot(checkUrl)
        }
    }

    /**
     * Show Fraud Alert Box
     */
    private fun showFraudAlert() {
        val alertDialog: AlertDialog =
            AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogTheme)
                .setTitle("Title")
                .setMessage("Are you sure?")
                .setNeutralButton(
                    R.string.proceed
                ) { p0, p1 ->
//                    val intent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
//                    sendBroadcast(intent)
                }
                .create()

        alertDialog.window?.setType(getSystemFlag())
        alertDialog.show()

//        if (eventType === AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
//// Check if the current activity is Chrome
//            if (event.getPackageName().equals("com.android.chrome")) {
//// Check if the event is a button click
//                if (event.getClassName().equals("android.widget.Button")) {
//// Check if the clicked button is the exit page button
//                    if (event.getText().toString().equalsIgnoreCase("Exit Page")) {
//// Send an intent to Chrome to close the current tab
//                        val intent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
//                        sendBroadcast(intent)
//                    } else if (event.getText().toString().equalsIgnoreCase("Proceed Anyway")) {
//// Do nothing or perform any other desired action
//                    }
//                }
//            }
//        }


//        if (eventAccess == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
//// Check if the current activity is Chrome
//            if (event.getPackageName().equals("com.android.chrome")) {
//// Check if the event is a button click
//                if (event.getClassName().equals("android.widget.Button")) {
//// Check if the clicked button is the exit page button
//                    if (event.getText().toString().equalsIgnoreCase("Exit Page")) {
//// Send an intent to Chrome to close the current tab
//                        val intent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
//                        sendBroadcast(intent)
//                    }
//                }
//            }
//        }

    }

    override fun onInterrupt() {

    }

    /**
     * Check Website is Secure or Not Api Call
     */
    private fun checkWebsiteSecureOrNot(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiRepository.checkWebsiteIsSecure(url)
                response.body()?.let {
                    val result = BrowserFraudUtils.parseJson(it)
                    if (result == "0") {
                        showFraudAlert()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}