package org.abormot.impl.db.service

import org.abormot.impl.db.entity.standard.Property
import org.abormot.impl.db.repository.standard.PropertyRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PropertyService(val propertyRepo: PropertyRepo) {

    fun getValue(id: Int): String? {
        return get(id)?.toString()
    }

    operator fun get(id: Int): Property? {
        val optional = propertyRepo.findById(id)
        return optional.orElse(null)
    }

}
