package com.linh.myapplication.data.remote.announcement

import okhttp3.Request
import okhttp3.ResponseBody
import okio.IOException
import okio.Timeout
import retrofit2.*
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.net.ssl.SSLHandshakeException

class CallAdapterFactory {
    @Suppress("ThrowableNotThrown")
    class CallAdapterFactory private constructor() : CallAdapter.Factory() {
        companion object {
            @JvmStatic
            @JvmName("create")
            operator fun invoke() = CallAdapterFactory()
        }

        override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {
            if (getRawType(returnType) != Call::class.java) {
                return null
            }

            check(returnType is ParameterizedType) {
                throw IllegalStateException(
                    "Response return type must be parameterized as Call<Resource2<Foo>> or Call<Resource2<out Foo>>"
                )
            }

            val responseType = getParameterUpperBound(0, returnType)

            if (getRawType(responseType) != Resource2::class.java) {
                return null
            }

            check(responseType is ParameterizedType) {
                "Response must be parameterized as Resource2<Foo> or Resource2<out Foo>"
            }

            val successBodyType = getParameterUpperBound(0, responseType)

            val converter = retrofit.nextResponseBodyConverter<Resource2<Any>>(
                null,
                successBodyType,
                annotations
            )

            return BodyCallAdapter(successBodyType, converter)
        }

        private class BodyCallAdapter<T : Any>(
            private val responseType: Type,
            private val converter: Converter<ResponseBody, Resource2<T>>
        ) : CallAdapter<T, Call<Resource2<T>>> {

            override fun responseType(): Type = responseType

            override fun adapt(call: Call<T>): Call<Resource2<T>> {
                return Resource2Call(call, converter)
            }
        }

        internal class Resource2Call<S : Any>(
            private val delegate: Call<S>,
            private val converter: Converter<ResponseBody, Resource2<S>>
        ) : Call<Resource2<S>> {
            override fun enqueue(callback: Callback<Resource2<S>>) {

                delegate.enqueue(object : Callback<S> {
                    override fun onFailure(call: Call<S>, t: Throwable) {
                        val apiResponse = when (t) {
                            is IOException, is SSLHandshakeException -> Resource2.error(
                                Exception("No network connection"),
                                null
                            )
                            //SSLHandshakeException is thrown when user's internet connection is disconnected
                            //before the server can return response
                            else -> {
                                Resource2.error(Exception("Unknown exception"), null)
                            }
                        }
                        callback.onResponse(this@Resource2Call, Response.success(apiResponse))
                    }

                    override fun onResponse(call: Call<S>, response: Response<S>) {
                        val body = response.body()
                        val error = response.errorBody()
                        val code = response.code()

                        if (response.isSuccessful && response.body() != null) {
                            callback.onResponse(
                                this@Resource2Call,
                                Response.success(Resource2.success(body!!))
                            )
                        } else {
                            val exception = Exception()

                            callback.onResponse(
                                this@Resource2Call,
                                Response.success(Resource2.error(exception, null))
                            )
                        }
                    }
                })
            }

            override fun isExecuted(): Boolean = delegate.isExecuted

            override fun timeout(): Timeout {
                return delegate.timeout()
            }

            override fun clone(): Call<Resource2<S>> = Resource2Call(delegate.clone(), converter)

            override fun isCanceled(): Boolean = delegate.isCanceled

            override fun cancel() = delegate.cancel()

            override fun execute(): Response<Resource2<S>> {
                throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
            }

            override fun request(): Request = delegate.request()
        }
    }
}