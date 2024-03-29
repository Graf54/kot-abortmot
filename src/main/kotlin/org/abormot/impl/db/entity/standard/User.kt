package org.abormot.impl.db.entity.standard


import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User {   // стандартные данные юзера
    @Id
    @Column(unique = true, nullable = false)
    var chatId: Long = 0
    @Column(length = 256)   //в клиенте под win максимум 255
    var firstName: String? = null
    @Column(length = 256)   //в клиенте под win максимум 255
    var lastName: String? = null
    @Column(length = 50)    //максимальная длинна 32 char на 22.11.2018 - делаем с небольшим запасом
    var userName: String? = null
    @Column(length = 50)    // берем с запасом
    var languageCode: String? = null
    var contactBot: Date? = null

}
