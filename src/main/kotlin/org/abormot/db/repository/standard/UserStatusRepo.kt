package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.User
import org.abormot.db.entity.standard.UserStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface UserStatusRepo : JpaRepository<UserStatus, Int> {
    fun findByUserAndStatus_Id(user: User, statusId: Int): Optional<UserStatus>

    fun countByUser_ChatIdAndStatus_Id(user_chat_Id: Long, statusId: Int): Int

    fun countByStatus_Id(statusId: Int): Int

    fun findAllByUser(user: User): List<UserStatus>

    @Transactional
    fun deleteByStatus_Id(statusId: Int): Int
}
