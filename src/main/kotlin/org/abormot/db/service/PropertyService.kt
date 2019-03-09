package org.abormot.db.service

import org.abormot.db.entity.standard.Property
import org.abormot.db.repository.standard.PropertyRepo
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
