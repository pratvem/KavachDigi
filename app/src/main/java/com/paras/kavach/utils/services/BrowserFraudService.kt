package com.paras.kavach.utils.services

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.util.Patterns
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


class BrowserFraudService : AccessibilityService() {
    private var browserApp = ""
    private var browserUrl = ""

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
            event.eventType == AccessibilityEvent.TYPE_WINDOWS_CHANGED ||
            event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
        ) {
            val parentNodeInfo = event.source ?: return
            val packageName = event.packageName.toString()
            var browserConfig: SupportedBrowserConfig? = null
            for (supportedConfig in getSupportedBrowsers()) {
                if (supportedConfig.packageName == packageName) {
                    browserConfig = supportedConfig
                }
            }

            if (browserConfig == null) {
                return
            }
            val capturedUrl = captureUrl(parentNodeInfo, browserConfig)
            parentNodeInfo.recycle()
            //   parentNodeInfo.refresh()
            if (capturedUrl == null) {
                return
            }

//            val eventTime = event.eventTime
            if (packageName != browserApp) {
                if (Patterns.WEB_URL.matcher(capturedUrl).matches()) {
                    Log.e("Browser", "$packageName  :  $capturedUrl")
//                    MainActivity.onBrowserRecv("$packageName  :  $capturedUrl")
                    browserApp = packageName
                    browserUrl = capturedUrl
                }
            } else {
                if (capturedUrl != browserUrl) {
                    if (Patterns.WEB_URL.matcher(capturedUrl).matches()) {
                        browserUrl = capturedUrl
                        Log.e("Browser", "$packageName   $capturedUrl")
//                        MainActivity.onBrowserRecv("$packageName  :  $capturedUrl")
                    }
                }
            }


        }

    }

    private class SupportedBrowserConfig(var packageName: String, var addressBarId: String)

    private fun getSupportedBrowsers(): List<SupportedBrowserConfig> {
        val browsers: MutableList<SupportedBrowserConfig> = ArrayList()
        browsers.add(SupportedBrowserConfig("com.android.chrome", "com.android.chrome:id/url_bar"))
        return browsers
    }

    private fun getChild(info: AccessibilityNodeInfo) {
        val i = info.childCount
        for (p in 0 until i) {
            val n = info.getChild(p)
            if (n != null) {
                val strres = n.viewIdResourceName
                if (n.text != null) {
                    val txt = n.text.toString()
                    Log.e("Track", "$strres  :  $txt")
                }
                getChild(n)
            }
        }
    }

    private fun captureUrl(info: AccessibilityNodeInfo, config: SupportedBrowserConfig): String? {
        val nodes = info.findAccessibilityNodeInfosByViewId(config.addressBarId)
        if (nodes == null || nodes.size <= 0) {
            return null
        }
        val addressBarNodeInfo = nodes[0]
        var url: String? = null
        if (addressBarNodeInfo.text != null) {
            url = addressBarNodeInfo.text.toString()
        }
        addressBarNodeInfo.recycle()
        return url
    }

    override fun onInterrupt() {

    }
}