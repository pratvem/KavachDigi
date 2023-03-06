package com.paras.kavach.utils

import com.paras.kavach.baseClasses.BaseResponse

sealed class NetworkResult<T> {

    class Success<T>(val data: T) : NetworkResult<T>()

    class Error<T>(val errorMessage: String, val error: BaseResponse? = null) : NetworkResult<T>()

    class Loading<T> : NetworkResult<T>()

}
