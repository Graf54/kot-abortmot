package org.abormot.editor

import org.abormot.impl.bot.util.BotUtil
import org.abormot.impl.db.entity.logic.Button
import org.abormot.impl.db.service.ButtonService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.util.*

interface IManagerHandler<Entity> {
    fun addHandler(buttonHandler: ButtonHandler<Entity>)

    @Throws(TelegramApiException::class)
    fun handle(update: Update)
}

class ManagerHandler<Entity>(private val botUtils: BotUtil, private val chatId: Long) : IManagerHandler<Entity> {
    private val map = HashMap<Int, ButtonHandler<Entity>>()
    private var currentHandler: ButtonHandler<*>? = null
    val entity: Entity? = null
    private val buttonService: ButtonService? = null

    override fun addHandler(buttonHandler: ButtonHandler<Entity>) {
        map[buttonHandler.buttonId] = buttonHandler
    }


    @Throws(TelegramApiException::class)
    override fun handle(update: Update) {
        if (entity == null) { // если данные удалили не реагировать на кнопки которые пользователь может отправлять вручную
            botUtils.sendMessage("Data is deleted", chatId)
            return
        }
        val button = buttonService!!.getButton(update)
        if (isCallHandler(button)) {
            return
        } else {
            if (currentHandler != null) {
                deleteKeyboardAfterFinish(botUtils.sendMessage(currentHandler!!.sendResponse(update).setChatId(chatId)))
            }
        }
    }

    @Throws(TelegramApiException::class)
    private fun isCallHandler(button: Button?): Boolean {
        if (button == null || button.id == 0) {
            return false
        }
        val buttonHandler = map[button.id]
        if (buttonHandler != null) {
            currentHandler = buttonHandler
            deleteKeyboardAfterFinish(botUtils.sendMessage(buttonHandler.sendRequest().setChatId(chatId)))

            return true
        }
        return false
    }

    private fun deleteKeyboardAfterFinish(messageId: Int) {
        //todo
//        SetDeleteMesseges.add(chatId, messageId)
    }
}
