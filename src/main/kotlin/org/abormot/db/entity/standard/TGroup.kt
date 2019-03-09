package org.abormot.db.entity.standard

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class TGroup { // имя group зарезервированно h2, поэтому делаем TGROUP

    @Id
    @Column(nullable = false, unique = true)
    var chatId: Long = 0
    var title: String? = null
    var isActive: Boolean = false
    var dateAdded: Date? = null
    //todo определить размер
    var comment: String? = null
    //todo определить размер
    var inviteLink: String? = null
}
