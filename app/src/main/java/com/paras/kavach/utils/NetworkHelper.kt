package com.paras.kavach.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.paras.kavach.baseClasses.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

/**
 * Check Internet (MOBILE/WIFI/ETHERNET)
 */
@Suppress("DEPRECATION")
fun Context.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities: NetworkCapabilities
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)!!
    } else {
        return connectivityManager.activeNetworkInfo?.isConnected!!
    }
    when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        }
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        }
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    "Check Internet Connection".shortToast(this)
    return false
}

/**
 * Safe call api and handle api response
 */
suspend fun <T : BaseResponse> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                // api call successful check if there is error or not
                if (response.body()?.success == 1) //no error
                    NetworkResult.Success(response.body()!!)
                else { //there is some error in api
                    NetworkResult.Error(response.message(), response.body())
                }
            } else {
                if (response.code() == 500) {
                    val error = BaseResponse()
                    error.success = 0
                    error.msg = "Some thing went wrong"
                    NetworkResult.Error(error.msg.toString(), error)
                } else if (response.code() == 401) {
                    NetworkResult.Error(response.message(), convertErrorBody(response.errorBody()))
                } else {
                    NetworkResult.Error(response.message(), convertErrorBody(response.errorBody()))
                }
            }
        } catch (throwable: Throwable) {
            val error = BaseResponse()
            error.success = 0
            when (throwable) {
                is java.net.UnknownHostException -> {
                    error.msg = "No Internet Connection"
                }
                is IOException -> {
                    error.msg = "Weak Internet or No Internet Connection"
                }
                else -> {
                    error.msg = throwable.localizedMessage
                }
            }
            NetworkResult.Error(error.msg.toString(), error)
        }
    }
}

/**
 * convert error body to base response
 */
private fun convertErrorBody(errorBody: ResponseBody?): BaseResponse? {
    return try {
        Gson().fromJson(errorBody!!.string(), BaseResponse::class.java)
    } catch (exception: Exception) {
        val error = BaseResponse()
        error.success = 0
        error.msg = exception.localizedMessage
        error
    }
}