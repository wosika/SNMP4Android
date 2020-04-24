package com.wosika.smnp


data class ResponseState(
    val isSuccess: Boolean,
    val value: String? = null,
    val exception: SnmpException? = null
)

class SnmpException : Exception {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}