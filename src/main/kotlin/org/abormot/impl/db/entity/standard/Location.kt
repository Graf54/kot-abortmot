package org.abormot.impl.db.entity.standard;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Location {
    @Id
    @GeneratedValue
    var id = 0
    var latitude: Float = 0.0F
    var longitude: Float = 0.0F
}
