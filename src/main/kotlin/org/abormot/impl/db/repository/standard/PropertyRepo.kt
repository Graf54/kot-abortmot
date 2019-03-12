package org.abormot.impl.db.repository.standard

import org.abormot.impl.db.entity.standard.Property
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PropertyRepo : CrudRepository<Property, Int>
