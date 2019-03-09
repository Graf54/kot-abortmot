package org.abormot.db.entity.standard

import javax.persistence.*

@Entity
class UserStatus {
    constructor(id: Int, user: User?, status: Status?) {
        this.id = id
        this.user = user
        this.status = status
    }

    @Id
    @GeneratedValue
    var id: Int = 0
    @ManyToOne
    @JoinColumn(nullable = false)
    var user: User? = null
    @ManyToOne
    @JoinColumn(nullable = false)
    var status: Status? = null
}
