package com.paras.kavach.data.repository

import com.paras.kavach.data.api.ApiService
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

   // suspend fun userLogin(map: HashMap<String, String>) = apiService.userLogin(map)
}