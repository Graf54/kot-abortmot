package org.abormot.editor.button.impl

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class FinishHandlerButton : ButtonEntityHandler() {
    fun sendRequest(): SendMessage {
        return super.sendRequest()
    }

    fun sendResponse(update: Update): SendMessage {
        return super.sendRequest()
    }
}
