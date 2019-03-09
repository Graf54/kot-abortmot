package org.abormot.db.entity.business

import org.abormot.db.Const
import java.util.*
import javax.persistence.*

@Entity
class ProfileAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    var chatId: Long = 0
    @ManyToOne
    var profile: Profile? = null
    @Column(length = Const.MESSAGE, nullable = false)
    var answer: String? = null
    @Column(nullable = false)
    var created: Date? = null
}
