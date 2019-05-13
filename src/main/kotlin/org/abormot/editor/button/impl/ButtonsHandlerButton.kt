package org.abormot.editor.button.impl


import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class ButtonsHandlerButton : ButtonEntityHandler() {

    private val description: String
        get() {
            if (descriptionCommands == null) {
                val stringBuilder = StringBuilder()
                CommandFactory.getDescription().forEach({ y, x -> stringBuilder.append(String.format("№%d - %s\n", y, x)) })
                descriptionCommands = stringBuilder.toString()
            }
            return descriptionCommands
        }

    private val listButton: String
        get() {
            val stringBuilder = StringBuilder()
            stringBuilder.append("id-commandId-name\n")
            ButtonService.getAll(LangService.getCurrentLang()).forEach { x -> stringBuilder.append(String.format("%d-%d-%s\n", x.getButtonId().getId(), x.getCommandId(), x.getName())) }
            return stringBuilder.toString()
        }

    fun sendRequest(): SendMessage {
        val lang = getEntity().getLang()
        val message = MessageService.getMessage(getEntity().getMessageId().getId(), lang)
        var buttonIds = "Клавиатура не задана"
        if (message.getKeyboard() != null) {
            buttonIds = message.getKeyboard().getButtonIds()
        }

        return SendMessage().setText(String.format("Текущая клавиатура: \n%s\n%s", getKeyboard(buttonIds, lang), buttonIds))
    }

    private fun getKeyboard(buttonsIds: String, lang: Lang): String {
        val keyDesc = StringBuilder()
        for (rows in buttonsIds.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            for (buttonId in rows.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                keyDesc.append(String.format("|< №%s : %s >| ", buttonId, ButtonService.getButton(Integer.parseInt(buttonId), lang).getName()))
            }
            keyDesc.append("\n")
        }
        return keyDesc.toString()
    }

    fun sendResponse(update: Update): SendMessage? {
        if (update.hasMessage() && update.message.text != null) {
            val text = update.message.text
            if (text.equals("/infoCommand", ignoreCase = true)) { //todo change to listCommand
                return SendMessage().setText(description)
            } else if (text.startsWith(newButton)) {
                return newButton(text)
            } else if (text.startsWith("/changeKeyboard")) {
                return addButton(update)
            } else if (text.equals("/listButton", ignoreCase = true)) {
                return SendMessage().setText(listButton)
            } else if (text.equals("/help", ignoreCase = true)) {
                return SendMessage().setText(MessageService.getText(5))
            }
        }
        return sendRequest()
    }

    private fun addButton(update: Update): SendMessage? {
        val buttonId = ButtonId()
        if (hasName(update)) {
            return SendMessage().setText("Такое имя уже используется")
        }
        //todo add real parametrs
        val buttonRu = Button(0, buttonId, LangService.getRu(), "", "", false, MessageId(0), 0)
        ButtonService.save(buttonRu)

        return null
    }

    private fun hasName(update: Update): Boolean {
        //todo realize
        return true
    }

    private fun newButton(text: String): SendMessage? {
        if (text.equals(newButton, ignoreCase = true)) {
            return SendMessage().setText("Для создания новой кнопки отправьте следующий текст:\n" +
                    newButton + "\n" +
                    "Name: О нас\n" +
                    "Lang: Ru\n" +
                    "Mess: Сообщение(оставить пустым если не нужно -> \"Mess:\")\n" +
                    newButton + "\n" +
                    "Name: О нас\n" +
                    "Lang: Ru\n" +
                    "Mess: Сообщение(оставить пустым если не нужно -> \"Mess:\")\n" +
                    "Keyb: 1,2;3,4 (список кнопок(их номера) через , и ; )")
        } else {
            val buttonParamtrs = text.split((newButton + "\n").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            createButton(buttonParamtrs[0])
        }
        return null
    }

    private fun createButton(parametrsButton: String): Button {
        /*
            Name: О нас
            Lang: Ru
            Mess: Сообщение(оставить пустым если не нужно -> "Mess:")
            Keyb: 1,2;3,4 (список кнопок(их номера) через , и ; )
        * */
        val parametrs = parametrsButton.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Button(0, null, LangService.getLangByName(parametrs[1]), parametrs[0], null, false, null, 0)
    }

    companion object {
        private var descriptionCommands: String? = null
        private val newButton = "/newButton"
        private val newButtonHelp = "Отправьте данные в следующем формате(скопируйте текст ниже и замените данные образца на свои):\n" +
                newButton + "\n" +
                "Name: О нас\n" +
                "Lang: Ru\n" +
                "Comm: 1(Номер команды)\n" +
                "Mess: Сообщение(оставить пустым если не нужно -> \"Mess:\")\n" +
                "Keyb: 1,2;3,4 (список кнопок(их номера) через , и ; ,оставить пустым если не нужно -> \"Keyb:\")"
    }
}
