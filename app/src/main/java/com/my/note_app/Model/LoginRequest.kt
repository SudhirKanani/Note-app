package com.my.note_app.Model

data class LoginRequest(
    val mobile: String,
    val pass: String,
    val firebase_token: String

    )