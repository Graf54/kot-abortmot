package org.abormot.editor.button.impl

import com.qbots.handler.editor.button.ButtonEntityHandler
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class MapHandlerButton : ButtonEntityHandler() {
    fun sendRequest(): SendMessage {
        return super.sendRequest()
    }

    fun sendResponse(update: Update): SendMessage {
        return super.sendRequest()
    }
}
