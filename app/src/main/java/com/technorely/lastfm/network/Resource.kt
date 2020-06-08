package com.technorely.lastfm.network

data class Resource<out T>(val status: EStatus, val data: T?, val error: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(EStatus.SUCCESS, data, null)
        }

        fun <T> error(error: String?, data: T?): Resource<T> {
            return Resource(EStatus.ERROR, data, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(EStatus.LOADING, data, null)
        }
    }
}