package org.abormot.impl.db.entity.logic

import org.abormot.impl.db.Const
import javax.persistence.*

@Entity
@Cacheable
class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    @Column(length = Const.BUTTON_NAME, nullable = false)
    var name: String = ""
    @Column(length = Const.MESSAGE)
    var url: String? = null
    @ManyToOne
    var message: Message? = null
    var commandId: Int = 0
}
