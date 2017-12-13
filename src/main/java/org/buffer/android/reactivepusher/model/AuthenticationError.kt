package org.buffer.android.reactivepusher.model

class AuthenticationError(val error: String, exception: Exception) : Exception(exception)