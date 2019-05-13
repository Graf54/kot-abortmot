package org.abormot.impl.command

import org.abormot.impl.bot.util.UpdateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.meta.api.objects.Update

open class UpdateService {
    protected var chatId: Long = 0
    protected var updateText: String? = null
    protected var update: Update? = null
    fun parseUpdate(update: Update) {
        this.update = update
        chatId = UpdateUtil.getChatId(update)
        if (hasText(update)) {
            updateText = update.message.text
        }
    }

    protected fun hasText(update: Update) = update.hasMessage() && update.message.hasText()
}