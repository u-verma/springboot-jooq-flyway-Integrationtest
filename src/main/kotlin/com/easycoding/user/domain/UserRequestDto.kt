package com.easycoding.user.domain

data class UserRequestDto(
    val firstName: String,
    val lastName: String,
    val emailId: String,
    val phoneNo: String
)