package org.abormot.editor.button.impl

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

import com.qbots.service.entity.logic.MessageService.getText

class TFileHandlerButton : ButtonEntityHandler() {
    fun sendRequest(): SendMessage {
        return SendMessage().setText(getText(57))
    }

    fun sendResponse(update: Update): SendMessage {
        val sendMessage = SendMessage()
        val entity = getMessage()
        if (update.hasMessage()) {
            val tFile = UpdateUtil.getTFile(update)
            if (tFile != null) {
                MessageService.addTFile(entity, tFile)
                return sendMessage.setText(getText(45))//"Файл добавлен" -> > ✅ Операция выполнена успешно
            }
            if (hasMessageText(update)) {
                if (update.message.text.equals(delete, ignoreCase = true)) {
                    val tFileList = entity.getTFileList()
                    if (tFileList != null) {
                        entity.setTFileList(null)
                        MessageService.save(entity)
                        TFileService.delete(tFileList)
                    }
                    return sendMessage.setText(getText(45)) //"Файл удален" -> > ✅ Операция выполнена успешно
                }
            }
        }
        return sendMessage.setText(getText(57))
    }
}
