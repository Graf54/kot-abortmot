package org.abormot.db.entity.business

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class ProfileUser {
    @Id
    var chatId: Long = 0
}
