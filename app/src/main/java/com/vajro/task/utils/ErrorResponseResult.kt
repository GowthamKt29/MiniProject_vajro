package com.vajro.task.utils

import java.io.Serializable

class ErrorResponseResult : Serializable {
    private val path: String? = null
    private val error: String? = null
    val message: String? = null
    private val timestamp: String? = null
    val status = 0
    val errorCode: String? = null
}