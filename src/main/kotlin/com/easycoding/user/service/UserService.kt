package com.easycoding.user.service

import com.easycoding.user.domain.UserRequestDto
import com.easycoding.user.persistence.UserRepository
import com.easycoding.user.persistence.entity.UserEntity
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun saveUser(userRequestDto: UserRequestDto): Boolean {
        val userEntity = userRequestDto.toUserEntity()
        userEntity.id = UUID.randomUUID()
        return userRepository.saveUser(userEntity)
    }

    fun getAllUsers(): List<UserEntity> {
        return userRepository.findAllUser().toList()
    }

    fun getUser(id: String): UserEntity? {
        return userRepository.findByUserId(UUID.fromString(id))
    }

    fun updateUser(id: String, userRequestDto: UserRequestDto): Boolean {
        val userEntity = userRequestDto.toUserEntity()
        userEntity.id = UUID.fromString(id)
        return userRepository.updateUser(userEntity)
    }

    fun deleteUser(id: String) {
        userRepository.deleteUserById(UUID.fromString(id))
    }
}

private fun UserRequestDto.toUserEntity() = UserEntity(
    firstName = this.firstName,
    lastName = this.lastName,
    emailId = this.emailId,
    phoneNo = this.phoneNo
)