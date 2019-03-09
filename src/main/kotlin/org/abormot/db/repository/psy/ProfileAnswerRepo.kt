package org.abormot.db.repository.psy

import org.abormot.db.entity.business.Profile
import org.abormot.db.entity.business.ProfileAnswer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface ProfileAnswerRepo : JpaRepository<ProfileAnswer, Int> {

    val allByOrderByChatIdAscProfileAsc: List<ProfileAnswer>
    @Transactional
    fun deleteByChatIdAndProfile(chatId: Long, profile: Profile)

    @Query(value = "SELECT count(*) FROM PSY.PROFILE_ANSWER AS a" + " LEFT JOIN PSY.PROFILE AS p ON a.PROFILE_ID = p.ID WHERE a.CHAT_ID = ?1 ", nativeQuery = true)
    fun countByChatId(chatId: Long): Int
}
