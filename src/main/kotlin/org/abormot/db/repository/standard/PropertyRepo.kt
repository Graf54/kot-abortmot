package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.Property
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PropertyRepo : CrudRepository<Property, Int>
