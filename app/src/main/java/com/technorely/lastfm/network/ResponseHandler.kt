package com.technorely.lastfm.network

import android.util.Log
import java.lang.Exception

class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        Log.e(ResponseHandler::class.java.simpleName, "handleException ", e)
        return Resource.error(e.message, null)
    }
}