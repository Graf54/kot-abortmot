package org.abormot.db.entity.standard

import javax.persistence.*

@Entity
class Status {
    @Id
    var id = 0
    @Column(nullable = false)
    var name = ""
    @Column(nullable = false)
    var comment = ""
    @ManyToOne
    @JoinColumn(nullable = false)
    var type: Type = Type()
}
