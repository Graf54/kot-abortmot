package org.abormot.init

import org.abormot.impl.db.entity.logic.Button
import org.abormot.impl.db.entity.logic.Message
import org.abormot.impl.db.entity.standard.Status
import org.abormot.impl.db.entity.standard.Type
import org.abormot.impl.db.service.*
import org.springframework.stereotype.Service

@Service
class FirstInit(val buttonService: ButtonService, val messageService: MessageService, val keyboardService: KeyboardService, val typeService: TypeService, val statusService: StatusService) {

    fun checkAndInit() {
        buttonService.save(getButton(1, "/start", null, getMessage(1, "first Message"), 1))
        buttonService.save(getButton(2, "/admin", null, getMessage(1, "first Message"), 1))
        buttonService.save(getButton(3, "Users", null, null, 4))
        val userType = getType(1, "Пользователи")

        getStatus(1, "Админ", "админы", userType)
        getStatus(2, "Бан", "забаненные", userType)
        getStatus(3, "Зарегистрированные", "регистрация", userType)
    }

    private fun getMessage(id: Int, text: String): Message? {
        val message = Message()
        message.id = id
        message.text = text
        return message
    }

    private fun getType(id: Int, name: String): Type {
        val type = Type()
        type.id = id
        type.name = name
        return type
    }

    private fun getStatus(id: Int, name: String, comment: String, type: Type): Status {
        val status = Status()
        status.id = id
        status.name = name
        status.comment = comment
        status.type = type
        return status
    }

    private fun getButton(id: Int, name: String, url: String?, message: Message?, commandId: Int): Button {
        val button = Button()
        button.id = id
        button.name = name
        button.url = url
        button.commandId = commandId
        button.message = message
        return button
    }
}