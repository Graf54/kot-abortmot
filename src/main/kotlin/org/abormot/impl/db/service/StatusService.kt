package org.abormot.impl.db.service

import org.abormot.impl.db.entity.standard.Status
import org.abormot.impl.db.entity.standard.Type
import org.abormot.impl.db.repository.standard.StatusRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StatusService(val statusRepo: StatusRepo) {

    fun getStatus(id: Int): Status {
        return statusRepo.findById(id).get()
    }

    fun getStatus(type: Type): List<Status> {
        return statusRepo.findAllByTypeId(type.id)
    }
}
