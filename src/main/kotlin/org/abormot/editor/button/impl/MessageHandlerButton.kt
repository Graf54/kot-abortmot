package org.abormot.editor.button.impl

import org.abormot.editor.button.ButtonEntityHandler
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class MessageHandlerButton : ButtonEntityHandler() {
    fun sendRequest(): SendMessage {
        return SendMessage().setText(getText(53))
    }

    fun sendResponse(update: Update): SendMessage {
        val entity = getMessage()
        if (update.hasMessage() && update.message.text != null) {
            val parserMessageEntity = ParserMessageEntity()
            entity.setText(parserMessageEntity.parseEntityToStringTag(UpdateUtil.getMessage(update)))
            MessageService.save(entity)
            return sendMainEditor()
        } else {
            return SendMessage().setText(wrongData())
        }
    }
}
