package org.abormot.editor.button.impl

import com.qbots.entity.logic.Button
import com.qbots.entity.logic.Message
import com.qbots.handler.editor.button.ButtonEntityHandler
import com.qbots.service.entity.logic.ButtonService
import com.qbots.tool.ButtonUtil
import com.qbots.tool.StringUtil
import com.qbots.tool.keyboard.ButtonsLeaf
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

import java.util.ArrayList
import java.util.stream.Collectors

import com.qbots.service.entity.logic.MessageService.getText

class LinkHandlerButton : ButtonEntityHandler() {
    private var buttonsLinks: ButtonsLeaf? = null
    private var links: MutableList<Button>? = null
    private var currentLink: Button? = null
    private val splitLink = " - "
    private val add = getText(46) // Добавить:

    fun sendRequest(): SendMessage {
        return sendLinks()
    }

    private fun sendLinks(): SendMessage {
        val entity = getMessage()
        loadLinks(entity)
        val sendMessage = SendMessage().setText(getText(47))  // Чтобы добавить новую ссылку ...
        if (links!!.size != 0) {
            buttonsLinks = ButtonsLeaf(links!!.stream().map(Function<Button, Any> { Button.getName() }).collect(Collectors.toList<Any>()))
            sendMessage.replyMarkup = buttonsLinks!!.getListButton()
        }
        return sendMessage
    }

    private fun loadLinks(message: Message) {
        links = ArrayList<Button>()
        if (message.getKeyboard() != null) {
            for (s in message.getKeyboard().getButtonIds().split("[,;]")) {
                val button = ButtonService.getButton(Integer.parseInt(s), getEntity().getLang())
                if (button.getUrl() != null) {
                    links!!.add(button)
                }
            }
        }
    }

    fun sendResponse(update: Update): SendMessage {
        if (update.hasCallbackQuery()) {
            currentLink = links!![Integer.parseInt(update.callbackQuery.data)]
            return SendMessage().setText(String.format(getText(59), currentLink!!.getName(), currentLink!!.getUrl())) //Текущая ссылка: %s - %s Отправьте новую ссылку в таком формате: название - ссылка, или напишите Удалить для удаления ссылки.
        }
        if (hasMessageText(update)) {
            val updateMessageText = update.message.text
            if (currentLink != null) {
                if (updateMessageText.contains(splitLink)) {
                    val split = updateMessageText.split(splitLink.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val name = split[0]
                    val link = split[1]
                    if (StringUtil.isValidateLink(link)) {
                        val buttonName = ButtonUtil.getButtonName(name)
                        if (ButtonService.isExist(buttonName, getEntity().getLang())) {
                            return SendMessage().setText(getText(32)) //"Такое название уже используется, попробуйте другое название"
                        }
                        currentLink!!.setName(ButtonUtil.getButtonName(name))
                        currentLink!!.setUrl(link)
                        ButtonService.save(currentLink)

                        return SendMessage().setText(getText(45))//42 "Ссылка обновлена" -> 45 ✅ Операция выполнена успешно
                    } else {
                        return SendMessage().setText(String.format(getText(43), link))//Ссылка не корректна - %s
                    }
                }
            }
        }
        return SendMessage().setText(wrongData())
    }
}
