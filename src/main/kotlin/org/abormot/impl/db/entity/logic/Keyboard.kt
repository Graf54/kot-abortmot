package org.abormot.impl.db.entity.logic

import javax.persistence.*

@Entity
class Keyboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    var buttonIds: String? = null
    var isInline: Boolean = false
    @Column(nullable = false)
    var comment: String? = null
}
