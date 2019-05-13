package org.abormot.editor

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

abstract class ButtonHandler<Entity>(val buttonId: Int, private val managerHandler: ManagerHandler<Entity>) {


    fun getEntity(): Entity? {
        return managerHandler.entity
    }

    open fun sendRequest(): SendMessage {
        return SendMessage().setText("Не реализовано")
    }

    open fun sendResponse(update: Update): SendMessage {
        return SendMessage().setText("Не реализовано")
    }
}
