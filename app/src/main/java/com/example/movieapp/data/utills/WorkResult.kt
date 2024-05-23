package com.example.movieapp.data.utills

sealed class WorkResult<out R> {
    data class Success<out T>(val data: T) : WorkResult<T>()
    data class Error(val exception: Exception) : WorkResult<Nothing>()
    object Loading : WorkResult<Nothing>()
}


/*enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
data class WorkResult<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> Success(data: T?): WorkResult<T> {
            return WorkResult(Status.SUCCESS, data, null)
        }
        fun <T> Error(msg: String): WorkResult<T> {
            return WorkResult(Status.ERROR, null, msg)
        }
        fun <T> Loading(): WorkResult<T> {
            return WorkResult(Status.LOADING, null, null)
        }
    }
}*/
