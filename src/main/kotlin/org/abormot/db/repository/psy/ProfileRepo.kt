package org.abormot.db.repository.psy

import org.abormot.db.entity.business.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepo : JpaRepository<Profile, Int> {
    fun findAllByOrderById(): List<Profile>

    @Query(value = "SELECT * FROM PSY.PROFILE ORDER BY ID LIMIT ?1 OFFSET ?2", nativeQuery = true)
    fun findAllOrderByIdLimitOffset(limit: Int, offset: Int): List<Profile>
}
