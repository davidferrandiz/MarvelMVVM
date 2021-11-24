package com.davidferrandiz.api

import com.davidferrandiz.setup.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * A network interceptor to manage all requests sent to the Marvel API.
 */
class MarvelApiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        var request: Request = chain.request()

        val timestamp = System.currentTimeMillis().toString()

        val url: HttpUrl = request.url().newBuilder()
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("apikey", BuildConfig.MARVEL_PUBLIC_KEY)
            .addQueryParameter("hash", RequestManager.buildHashQuery(timestamp))
            .build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }

}
