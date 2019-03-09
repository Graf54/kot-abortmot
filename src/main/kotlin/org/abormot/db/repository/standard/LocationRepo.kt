package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.Location
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepo : CrudRepository<Location, Int>