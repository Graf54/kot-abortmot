package org.abormot.impl.db.repository.standard

import org.abormot.impl.db.entity.standard.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StatusRepo : JpaRepository<Status, Int> {
    fun findAllByTypeId(typeId: Int): List<Status>
}
