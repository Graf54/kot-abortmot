package org.abormot.impl.db.repository.psy

import org.abormot.impl.db.entity.business.ProfileUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileUserRepo : JpaRepository<ProfileUser, Long> {
    fun countByChatId(chatId: Long): Int
}
