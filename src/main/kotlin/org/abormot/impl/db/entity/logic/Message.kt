package org.abormot.impl.db.entity.logic

import org.abormot.impl.db.Const
import org.abormot.impl.db.entity.standard.Location
import org.abormot.impl.db.entity.standard.Files
import javax.persistence.*

@Entity
class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    @Column(length = Const.MESSAGE, nullable = false)
    var text: String = ""
    @ManyToOne
    var keyboard: Keyboard? = null
    @ManyToOne
    var files: Files? = null
    @ManyToOne
    var location: Location? = null
}
