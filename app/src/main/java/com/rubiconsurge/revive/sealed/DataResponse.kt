package com.rubiconsurge.revive.sealed

sealed class DataResponse<T>(
    var data: T? = null,
    var error: com.rubiconsurge.revive.sealed.Error? = null,
) {
    class Success<T>(data: T) : DataResponse<T>(data = data)
    class Error<T>(error: com.rubiconsurge.revive.sealed.Error) : DataResponse<T>(error = error)
}