package org.abormot.impl.db.repository.standard

import org.abormot.impl.db.entity.standard.User
import org.abormot.impl.db.entity.standard.UserLight
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<User, Long> {
    fun findByChatId(chatId: Long): User

    @Query(value = "SELECT u.* " +
            "FROM STANDARD.USER AS u " +
            "  LEFT JOIN STANDARD.USER_STATUS AS us ON u.CHAT_ID = us.USER_CHAT_ID AND us.STATUS_ID=?1", nativeQuery = true)
    fun getByStatusId(statusId: Int): List<User>

    fun countByChatId(chatId: Long): Int

    @Query(value = "SELECT u.FULL_NAME AS fullName, u.CHAT_ID AS chatId " + "FROM STANDARD.USER AS u WHERE u.PHONE IS NOT NULL ORDER BY DATE_REG LIMIT ?1 OFFSET ?2", nativeQuery = true)
    fun getAllRegistr(limit: Int, offset: Int): List<UserLight>

    @Query(value = "SELECT u.FULL_NAME AS fullName, u.CHAT_ID AS chatId " + "FROM STANDARD.USER AS u ORDER BY DATE_REG LIMIT ?1 OFFSET ?2", nativeQuery = true)
    fun getAll(limit: Int, offset: Int): List<UserLight>

    fun countByPhoneNotNull(): Int

    @Query(value = "SELECT u.FULL_NAME AS fullName, u.CHAT_ID AS chatId " +
            "FROM STANDARD.USER AS u " +
            "  LEFT JOIN STANDARD.USER_STATUS AS us ON u.CHAT_ID = us.USER_CHAT_ID WHERE us.STATUS_ID=?1 ORDER BY u.CHAT_ID LIMIT ?2 OFFSET ?3", nativeQuery = true)
    fun getAll(statusId: Int, limit: Int, offset: Int): List<UserLight>

    fun findTop30ByPhoneLikeIgnoreCaseOrderByChatId(phone: String): List<User>

    fun findTop30ByUserNameLikeIgnoreCaseOrderByChatId(phone: String): List<User>
}
