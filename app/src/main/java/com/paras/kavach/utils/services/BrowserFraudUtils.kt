package com.paras.kavach.utils.services

import android.view.accessibility.AccessibilityNodeInfo
import com.google.gson.JsonObject
import org.json.JSONObject

object BrowserFraudUtils {

    class SupportedBrowserConfig(var packageName: String, var addressBarId: String)

    fun getSupportedBrowsers(): List<SupportedBrowserConfig> {
        val browsers: MutableList<SupportedBrowserConfig> = ArrayList()
        browsers.add(SupportedBrowserConfig("com.android.chrome", "com.android.chrome:id/url_bar"))
        return browsers
    }

    fun captureUrl(info: AccessibilityNodeInfo, config: SupportedBrowserConfig): String? {
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

    fun parseJson(it: JsonObject): String {
        val columnArrayList = ArrayList<String>()
        val jsonObject = JSONObject(it.toString())
        val columnArray = jsonObject.getJSONArray("columns")
        val dataArray = jsonObject.getJSONArray("data").getJSONArray(0)
        for (x in 0 until columnArray.length()) {
            columnArrayList.add(columnArray.getString(x))
        }
        val index = columnArrayList.indexOf("result")
        return dataArray.getString(index)
    }

}