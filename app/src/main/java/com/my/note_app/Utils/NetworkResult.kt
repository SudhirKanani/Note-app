package com.my.note_app.Utils

import java.lang.Exception

sealed class NetworkResult<T>(val data: T? = null, val exception: Exception? = null, val error:String? = null) {
    class Success<T>(data: T) : NetworkResult<T>(data = data)
    class Exeption<T>(exception: Exception) : NetworkResult<T>(exception = exception)
    class Error<T>(error: String?):NetworkResult<T>(error = error)
    class Loading<T>() : NetworkResult<T>()
}