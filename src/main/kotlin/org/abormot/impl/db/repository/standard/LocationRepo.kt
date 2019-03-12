package org.abormot.impl.db.repository.standard

import org.abormot.impl.db.entity.standard.Location
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepo : CrudRepository<Location, Int>
