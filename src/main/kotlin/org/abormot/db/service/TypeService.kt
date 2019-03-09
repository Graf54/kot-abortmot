package org.abormot.db.service

import org.abormot.db.entity.standard.Type
import org.abormot.db.repository.standard.TypeRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TypeService(private val typeRepo: TypeRepo) {

    fun getType(id: Int): Type {
        return typeRepo.findById(id).get()
    }

    fun getTypeByButtonId(buttinId: Int): Type {
        return typeRepo.getTypeByButtonId(buttinId)
    }
}
