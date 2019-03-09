package org.abormot.db.entity.standard


import javax.persistence.*

@Entity
class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id = 0
    @Column(nullable = false)
    var name = ""
    @Column(nullable = false)
    var link = ""
}
