package com.easycoding.user.controller

import com.easycoding.user.domain.UserRequestDto
import com.easycoding.user.persistence.entity.UserEntity
import com.easycoding.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody userRequestDto: UserRequestDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok().body(userService.saveUser(userRequestDto))
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): UserEntity? {
        return userService.getUser(id)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody userRequestDto: UserRequestDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok().body(userService.updateUser(id, userRequestDto))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String) {
        userService.deleteUser(id)
    }
}