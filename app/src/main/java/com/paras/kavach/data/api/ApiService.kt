package com.paras.kavach.data.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun checkWebsiteIsSecure(
        @Url baseUrl: String
    ): Response<JsonObject>

}