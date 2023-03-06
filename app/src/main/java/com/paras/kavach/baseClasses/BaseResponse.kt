package com.paras.kavach.baseClasses

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("success")
    var success: Int? = null

    @SerializedName("msg")
    var msg: String? = null
}