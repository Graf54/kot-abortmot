package org.abormot.editor.button.impl

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class ChangeLangHandlerButton : ButtonEntityHandler() {
    fun sendRequest(): SendMessage {
        changeLang()
        return sendMainEditor()
    }

    private fun changeLang() {
        val entity = getEntity()
        val lang = LangService.switchLang(entity.getLang())
        val newButton = ButtonService.getButton(entity.getButtonId().getId(), lang)
        mangerHandler.setEntity(newButton)
    }

    fun sendResponse(update: Update): SendMessage {
        return SendMessage().setText("Выберите действие сначала")
    }
}
