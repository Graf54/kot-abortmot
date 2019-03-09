package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.Link
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkRepo : CrudRepository<Link, Int>
