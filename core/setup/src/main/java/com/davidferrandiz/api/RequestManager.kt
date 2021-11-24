package com.davidferrandiz.api

import com.davidferrandiz.setup.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest

fun String.toMD5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
}

object RequestManager {
    fun buildHashQuery(timestamp: String) =
        (timestamp + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_PUBLIC_KEY).toMD5()
}
