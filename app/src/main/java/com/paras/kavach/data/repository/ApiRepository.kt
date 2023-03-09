package com.paras.kavach.data.repository

import com.paras.kavach.BuildConfig
import com.paras.kavach.data.api.ApiService
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun checkWebsiteIsSecure(url: String) =
        apiService.checkWebsiteIsSecure(BuildConfig.BASE_URL.plus(url))

}