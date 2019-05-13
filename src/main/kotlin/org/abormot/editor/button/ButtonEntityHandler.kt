package org.abormot.editor.button

import org.abormot.editor.ButtonHandler
import org.abormot.editor.ManagerHandler
import org.abormot.impl.db.Const
import org.abormot.impl.db.entity.logic.Button
import org.abormot.impl.db.entity.logic.Message
import org.abormot.impl.db.service.ButtonService
import org.abormot.impl.db.service.KeyboardService
import org.abormot.impl.db.service.MessageService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import java.util.*

@Component
abstract class ButtonEntityHandler(buttonId: Int, managerHandler: ManagerHandler<Button>) : ButtonHandler<Button>(buttonId, managerHandler) {
    protected var delete = getText(58) //удалить;
    private var links: MutableList<Button>? = null
    protected val message: Message?
        get() = if (getEntity()!!.message!!.id != null) {
            messageService.getMessage(getEntity()!!.getMessageId().getId(), getEntity()!!.getLang())
        } else {
            null
        }

    fun getText(id: Int): String {
        return MessageService
    }

    private val isEvent: Boolean
        get() = false

    protected fun sendMainEditor(): SendMessage {
        var countFiles = 0
        val hasMap = Const.NO
        var messageText = "отсутствует"
        val message = message
        loadLinks(message)
        if (message != null) {
            countFiles = if (message!!.getTFileList() == null) 0 else message!!.getTFileList().getTFileList().size()
            messageText = messageText.abbreviate(message!!.getText(), 100)
            /*if (messageLocationDao.isExist(message.getId(), message.getLangId())) { //todo
                hasMap = Const.YES;
            }*/
        }
        val textEditInfo = String.format(getText(132), getEntity()!!.name, messageText, countFiles, links!!.size, hasMap)
        return SendMessage().setText(textEditInfo).setReplyMarkup(KeyboardService.select(19))
    }

    private fun loadLinks(message: Message?) {
        links = ArrayList()
        if (message != null && message!!.getKeyboard() != null) {
            for (s in message!!.getKeyboard().getButtonIds().split("[,;]")) {
                val button = ButtonService.getButton(Integer.parseInt(s), getEntity()!!.getLang())
                if (button.url != null) {
                    links!!.add(button)
                }
            }
        }
    }

    protected fun wrongData(): String {
        return getText(44)
    }
}
