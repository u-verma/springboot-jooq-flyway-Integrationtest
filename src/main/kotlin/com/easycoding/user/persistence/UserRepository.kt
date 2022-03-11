package com.easycoding.user.persistence

import com.easycoding.user.persistence.entity.UserEntity
import java.util.UUID
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import persistence.tables.records.UserPrincipalRecord
import persistence.tables.references.USER_PRINCIPAL
import java.util.function.Function as JFunction

@Repository
class UserRepository(private val dslContext: DSLContext) {

    fun findAllUser(): List<UserEntity> {
        return dslContext.select().from(USER_PRINCIPAL).fetch().map {
            userPrincipleRecordToUserEntity.apply(it as UserPrincipalRecord)
        }.toList()
    }

    fun findByUserId(userId: UUID): UserEntity? {
        return dslContext.selectFrom(USER_PRINCIPAL)
            .where(USER_PRINCIPAL.ID.eq(userId))
            .fetchOne()?.let { userPrincipleRecordToUserEntity.apply(it) }
    }

    fun saveUser(userEntity: UserEntity): Boolean {
        return 1 == dslContext
            .insertInto(USER_PRINCIPAL)
            .set(userEntityToUserPrincipalRecord.apply(userEntity))
            .execute()
    }

    fun updateUser(userEntity: UserEntity): Boolean {
        return 1 == dslContext
            .newRecord(USER_PRINCIPAL, userEntity).update()
    }

    fun deleteUserById(id: UUID): Boolean {
        return 1 == dslContext.deleteFrom(USER_PRINCIPAL)
            .where(USER_PRINCIPAL.ID.eq(id))
            .execute()
    }

    private val userPrincipleRecordToUserEntity: JFunction<UserPrincipalRecord, UserEntity> =
        JFunction<UserPrincipalRecord, UserEntity> {
            UserEntity(
                id = it.id,
                firstName = it.firstName!!,
                lastName = it.lastName!!,
                emailId = it.emailId!!,
                phoneNo = it.phoneNo!!
            )
        }

    private val userEntityToUserPrincipalRecord: JFunction<UserEntity, UserPrincipalRecord> =
        JFunction<UserEntity, UserPrincipalRecord> {
            UserPrincipalRecord(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                emailId = it.emailId,
                phoneNo = it.phoneNo
            )
        }
}
