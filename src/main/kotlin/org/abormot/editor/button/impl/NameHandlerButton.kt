package org.abormot.editor.button.impl

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

import com.qbots.service.entity.logic.MessageService.getText

class NameHandlerButton : ButtonEntityHandler() {
    fun sendRequest(): SendMessage {
        return SendMessage().setText(String.format(getText(125), getEntity().getName()))
    }

    fun sendResponse(update: Update): SendMessage {
        val entity = getEntity()
        if (update.hasMessage() && update.message.text != null) {
            val buttonName = ButtonUtil.getButtonName(update.message.text)
            if (ButtonService.isExist(buttonName, getEntity().getLang())) {
                return SendMessage().setText(getText(32))
            }
            entity.setName(buttonName)
            ButtonService.save(entity)
            return sendMainEditor()
        } else {
            return SendMessage().setText(wrongData())
        }
    }
}
