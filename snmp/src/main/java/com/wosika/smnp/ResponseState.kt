package com.wosika.smnp


data class ResponseState(
    val isSuccess: Boolean,
    val value: String? = null,
    val exception: SnmpException? = null
)

class SnmpException(errorMsg: String) : Exception(errorMsg)