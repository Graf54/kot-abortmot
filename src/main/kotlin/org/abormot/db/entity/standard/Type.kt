package org.abormot.db.entity.standard


import org.abormot.db.Const
import javax.persistence.*

@Entity
class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    @Column(length = Const.BUTTON_NAME)
    var name: String? = null
}
