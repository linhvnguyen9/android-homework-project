package com.linh.myapplication.data.remote.announcement

data class Resource2<out T>(val status: Status, val data: T?, val error: Throwable?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource2<T> {
            return Resource2(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: Throwable, data: T?= null): Resource2<T> {
            return Resource2(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T? = null): Resource2<T> {
            return Resource2(
                Status.LOADING,
                data,
                null
            )
        }
    }

    fun isSuccessful() = status == Status.SUCCESS

    fun isLoading() = status == Status.LOADING

    fun isError() = status == Status.ERROR
}

inline fun <X, Y> Resource2<X>.map(transform: (X?) -> Y): Resource2<Y> = Resource2(status, transform(data), error)
