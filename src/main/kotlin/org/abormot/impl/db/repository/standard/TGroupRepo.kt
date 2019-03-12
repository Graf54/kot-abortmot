package org.abormot.impl.db.repository.standard

import org.abormot.impl.db.entity.standard.TGroup
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TGroupRepo : CrudRepository<TGroup, Int>
