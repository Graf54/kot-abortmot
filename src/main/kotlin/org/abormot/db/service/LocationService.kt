package org.abormot.db.service

import org.abormot.db.entity.standard.Location
import org.abormot.db.repository.standard.LocationRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LocationService(val locationRepo: LocationRepo) {

    fun delete(location: Location) {
        locationRepo.delete(location)
    }

    fun save(location: Location) {
        locationRepo.save(location)
    }

    fun getById(id: Int): Location {
        return locationRepo.findById(id).get()
    }
}
