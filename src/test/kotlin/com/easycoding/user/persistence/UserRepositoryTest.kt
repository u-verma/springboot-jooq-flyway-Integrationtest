package com.easycoding.user.persistence

import com.easycoding.AbstractIntegrationTest
import com.easycoding.user.persistence.entity.UserEntity
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class UserRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `Should save user`() {
        val user = UserEntity(
            id = UUID.randomUUID(),
            firstName = "firstName",
            lastName = "lastName",
            emailId = "email@test.com",
            phoneNo = "+91787865465"
        )
        userRepository.saveUser(user)
        val savedUser = userRepository.findByUserId(user.id!!)!!
        assertThat(savedUser.id).isEqualTo(user.id)
    }
}