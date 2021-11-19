package com.edgarmarcopolo.neptune.api

object HttpCodes {
    const val SUCCESS = 200
    const val SUCCESS_NO_CONTENT = 204
    const val REDIRECTION = 300
    const val BAD_REQUEST_ERROR = 400
    const val UNAUTHORIZED_ERROR = 401
    const val FORBIDDEN_ERROR = 403
    const val NOT_FOUND_ERROR = 404
    const val INVALID_SERVER_ERROR = 500
    const val NO_NETWORK_RESPONSE_CODE = -1
    const val EXCEPTION_RESPONSE_CODE = 99999
}