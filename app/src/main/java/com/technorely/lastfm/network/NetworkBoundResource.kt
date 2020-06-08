package com.technorely.lastfm.network


abstract class NetworkBoundResource<ResultType: Any> {

    private val responseHandler = ResponseHandler()

    suspend fun execute(): Resource<ResultType> {
        return try {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                getFromNetwork()
            } else {
                loadDBResource()
            }
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    private suspend fun getFromNetwork(): Resource<ResultType> {
        val request = makeRequest()
        return when (request.status) {
            EStatus.SUCCESS -> {
                saveResultInDB(request.data!!)
                loadDBResource()
            }
            else -> {
                responseHandler.handleException(Exception(request.error))
            }
        }
    }

    private suspend fun makeRequest(): Resource< ResultType> {
        return try {
            responseHandler.handleSuccess(createCall()!!)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    private suspend fun loadDBResource(): Resource<ResultType> {
        return try {
            responseHandler.handleSuccess(loadFromDb()!!)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    abstract suspend fun saveResultInDB(item: ResultType)

    abstract suspend fun shouldFetch(data: ResultType?): Boolean

    abstract suspend fun loadFromDb(): ResultType

    abstract suspend fun createCall(): ResultType
}