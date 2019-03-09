package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.TGroup
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TGroupRepo : CrudRepository<TGroup, Int>
