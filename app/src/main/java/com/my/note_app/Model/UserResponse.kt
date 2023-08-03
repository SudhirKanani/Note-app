package com.my.note_app.Model

data class UserResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)