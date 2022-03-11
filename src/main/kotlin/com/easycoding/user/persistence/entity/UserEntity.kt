package com.easycoding.user.persistence.entity

import java.util.UUID

data class UserEntity(
    var id: UUID? = null,
    var firstName: String,
    var lastName: String,
    var emailId: String,
    var phoneNo: String
)
