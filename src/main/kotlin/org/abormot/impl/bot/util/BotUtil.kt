package org.abormot.impl.bot.util

import mu.KotlinLogging
import org.abormot.impl.db.Const
import org.abormot.impl.db.FileType
import org.abormot.impl.db.entity.logic.Button
import org.abormot.impl.db.entity.standard.Location
import org.abormot.impl.db.entity.standard.TFile
import org.abormot.impl.db.service.KeyboardService
import org.abormot.impl.db.service.MessageService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat
import org.telegram.telegrambots.meta.api.methods.send.*
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Contact
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMedia
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import java.io.File
import java.io.InputStream
import java.util.*
import java.util.stream.Collectors

@Service
class BotUtil(var bot: DefaultAbsSender, val messageService: MessageService, val keyboardService: KeyboardService) {
    private val logger = KotlinLogging.logger {}

    fun sendMessage(messgeId: Int, chatId: Long): Int {
        return sendMessage(messgeId, chatId, null)
    }

    fun sendMessage(messageId: Int, chatId: Long, contact: Contact?): Int {
        val message = messageService.getMessage(messageId)
        val sendMessage = SendMessage()
        sendMessage.text = message.text
        sendMessage.setChatId(chatId)
        sendMessage.setParseMode(ParseMode.HTML)
        sendLocation(message.location, chatId)
        if (message.keyboard != null) {
            sendMessage.replyMarkup = keyboardService.select(message.keyboard!!.id)
        }
        if (message.files != null) {
            sendFileListMediaGroup(chatId, message.files!!.tFileList!!)
        }
        val result = sendMessage(sendMessage)
        if (contact != null) {
            sendContact(chatId, contact)
        }
        return result
    }

    fun editMessage(text: String, keyboardMarkup: InlineKeyboardMarkup?, chatId: Long, messageId: Int) {
        if (keyboardMarkup == null) {
            bot.execute(EditMessageText()
                    .setText(text)
                    .setMessageId(messageId)
                    .setChatId(chatId))
            return
        }
        bot.execute(EditMessageText()
                .setText(text)
                .setMessageId(messageId)
                .setChatId(chatId)
                .setReplyMarkup(keyboardMarkup))
    }

    fun addButton(replyKeyboard: ReplyKeyboard?, buttonFromDb: Button): ReplyKeyboard? {
        var replyKeyboard = replyKeyboard
        if (replyKeyboard == null) {
            replyKeyboard = getInlineKeyboard(arrayOf(buttonFromDb.name), arrayOf(buttonFromDb.name))
            return replyKeyboard
        }
        try {
            val keyboard = replyKeyboard as InlineKeyboardMarkup?
            val rowButton = ArrayList<InlineKeyboardButton>()
            val button = InlineKeyboardButton()
            var buttonText = buttonFromDb.name
            button.text = buttonText
            if (buttonFromDb.url != null) {
                button.url = buttonFromDb.url
            } else {
                buttonText = if (buttonText.length < 64) buttonText else buttonText.substring(0, 64)
                button.callbackData = buttonText
            }
            button.callbackData = buttonFromDb.name
            rowButton.add(button)
            keyboard!!.keyboard.add(rowButton)
            return keyboard
        } catch (e: Exception) {
            val keyboard = replyKeyboard as ReplyKeyboardMarkup?
            val keyboardRow = KeyboardRow()
            val keyboardButton = KeyboardButton()
            keyboardButton.text = buttonFromDb.name
            keyboardRow.add(keyboardButton)
            keyboard!!.keyboard.add(keyboardRow)
            return keyboard
        }

    }

    fun getInlineKeyboard(namesButton: Array<String>, callbackMessage: Array<String>?): InlineKeyboardMarkup {
        val keyboard = InlineKeyboardMarkup()
        val rowsKeyboard = ArrayList<List<InlineKeyboardButton>>()
        var buttonIdsString: String
        var callbackIndex = 0
        for (i in namesButton.indices) {
            buttonIdsString = namesButton[i]
            val rowButton = ArrayList<InlineKeyboardButton>()
            val buttonIds = buttonIdsString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (buttonId in buttonIds) {
                val button = InlineKeyboardButton()
                button.text = buttonId
                if (callbackMessage == null) {
                    button.callbackData = buttonId
                } else {
                    button.callbackData = callbackMessage[callbackIndex++]
                }
                rowButton.add(button)
            }
            rowsKeyboard.add(rowButton)
        }
        keyboard.keyboard = rowsKeyboard
        return keyboard
    }

    fun sendContact(chatId: Long, contact: Contact): Int {
        return bot.execute<Message, SendContact>(SendContact()
                .setChatId(chatId)
                .setFirstName(contact.firstName)
                .setLastName(contact.lastName)
                .setPhoneNumber(contact.phoneNumber)
        ).messageId!!
    }

    fun sendPhoto(photo: String, chatId: Long) {
        try {
            bot.execute(SendPhoto().setChatId(chatId).setPhoto(photo))
        } catch (e: TelegramApiException) {
            sendMessage("Can't send photo", chatId, ParseMode.HTML)
        }

    }

    fun sendMessage(text: String, chatId: Long): Int {
        return sendMessage(text, chatId, ParseMode.HTML)
    }

    fun sendMessage(text: String, chatId: Long, parseMode: String?): Int {
        val sendMessage = SendMessage()
        sendMessage.setChatId(chatId)
        sendMessage.text = text
        if (parseMode == null) {
            sendMessage.setParseMode(null)
        } else {
            sendMessage.setParseMode(parseMode)
        }
        return sendMessage(sendMessage)
    }

    fun sendMessage(text: String, chatId: Long, contact: Contact?) {
        val sendMessage = SendMessage()
        sendMessage.setChatId(chatId)
        sendMessage.text = text
        sendMessage.setParseMode(ParseMode.HTML)
        sendMessage(sendMessage)
        if (contact != null) {
            sendContact(chatId, contact)
        }
    }

    fun deleteInlineKeyboard(chatId: Long, messId: Int) {
        try {
            bot.execute(EditMessageReplyMarkup()
                    .setChatId(chatId)
                    .setMessageId(messId)
                    .setReplyMarkup(null)
            )
        } catch (ignored: TelegramApiException) {
        }

    }

    fun deleteMessage(chatId: Long, messageId: Int) {
        try {
            bot.execute(DeleteMessage(chatId, messageId))
        } catch (e: TelegramApiException) {
        }

    }

    fun sendMessage(sendMessage: SendMessage): Int {
        try {
            sendMessage.disableWebPagePreview()
            sendMessage.enableHtml(true)
            return bot.execute<Message, SendMessage>(sendMessage).messageId!!
        } catch (e: TelegramApiRequestException) {
            if (e.apiResponse.contains("Bad Request: can't parse entities")) {
                sendMessage.setParseMode(null)
                val addText = "\n- WRONG TAGS"
                if (sendMessage.text.length + addText.length > Const.MESSAGE) {
                    sendMessage.text = sendMessage.text.substring(Const.MESSAGE - addText.length) + addText
                } else {
                    sendMessage.text = sendMessage.text + addText
                }
                return bot.execute<Message, SendMessage>(sendMessage).messageId!!
            } else
                throw e
        }

    }

    fun sendAnswer(text: String, chatId: Long, messId: Int): Int {
        val sendMessage = SendMessage(chatId, text)
                .setReplyToMessageId(messId)
        return sendMessage(sendMessage)
    }

    fun sendMessageWithKeyboard(text: String, keyboard: ReplyKeyboard, chatID: Long, replyMessId: Int = 0): Int {
        val sendMessage = SendMessage()
                .setParseMode(ParseMode.HTML)
                .setChatId(chatID)
                .setText(text)
                .disableWebPagePreview()
                .setReplyMarkup(keyboard)
        if (replyMessId != 0) {
            sendMessage.replyToMessageId = replyMessId
        }
        return sendMessage(sendMessage)
    }

    fun hasContactOwner(update: Update): Boolean {
        return if (update.hasMessage() && update.message.contact != null) {
            update.message.from.id == update.message.contact.userID
        } else false
    }

    fun sendFileList(chatIdOperator: Long, fileList: List<TFile>) {
        fileList.forEach { tFile ->
            try {
                sendFile(tFile, chatIdOperator)
            } catch (e: Exception) {
                logger.error("Can't send tFile {}", tFile.toString())
                logger.error("Cause: ", e)
            }
        }
    }

    fun sendFileListMediaGroup(chatId: Long, list: List<TFile>) {
        val listMedia = list.stream().filter { tFile -> tFile.typeId == 1 || tFile.typeId == 3 }.collect(Collectors.toList<TFile>())
        val listNoMedia = list.stream().filter { tFile -> tFile.typeId != 1 && tFile.typeId != 3 }.collect(Collectors.toList<TFile>())
        val inputMedia = ArrayList<InputMedia<*>>()
        listMedia.forEach { tFile ->
            if (tFile.typeId == 1) {
                inputMedia.add(InputMediaPhoto(tFile.link, tFile.name))
            } else if (tFile.typeId == 3) {
                inputMedia.add(InputMediaVideo(tFile.link, tFile.name))
            }
        }
        if (inputMedia.size != 0) {
            bot.execute(SendMediaGroup(chatId, inputMedia))
        }
        sendFileList(chatId, listNoMedia)
    }


    fun sendFileListThread(chatIdOperator: Long, fileList: List<TFile>) {
        Thread {
            fileList.forEach { x ->
                try {
                    Thread.sleep(200)
                    sendFile(x, chatIdOperator)
                } catch (e: Exception) {
                    logger.error("Can't send tFile {}", x.toString())
                    logger.error("Cause: ", e)

                }
            }
        }.start()
    }

    fun sendFile(file: TFile, chatId: Long): Int {
        try {
            when (file.typeId) {
                FileType.FILE_PHOTO.id -> return bot.execute(SendPhoto()
                        .setChatId(chatId)
                        .setPhoto(file.link)
                ).messageId!!
                FileType.FILE_DOCUMENT.id -> return bot.execute(SendDocument()
                        .setChatId(chatId)
                        .setDocument(file.link)
                ).messageId!!
                FileType.FILE_VIDEO.id -> return bot.execute(SendVideo()
                        .setChatId(chatId)
                        .setVideo(file.link)
                ).messageId!!
                FileType.FILE_AUDIO.id -> return bot.execute(SendAudio()
                        .setChatId(chatId)
                        .setAudio(file.link)
                ).messageId!!
                FileType.FILE_VOICE.id -> return bot.execute(SendVoice()
                        .setChatId(chatId)
                        .setVoice(file.link)
                ).messageId!!
                else -> return bot.execute(SendDocument()
                        .setChatId(chatId)
                        .setDocument(file.link)
                ).messageId!!
            }
        } catch (e: TelegramApiException) {
            logger.error("Can't send by userChatId: {} - file:{}", chatId, file.toString())
            logger.error("Reason: ", e)
            return -1
        }

    }

    fun sendLocation(location: Location?, chatId: Long): Int {
        var messageId = 0
        if (location != null) {
            if (location.latitude != 0.0f && location.longitude != 0.0f) {
                messageId = bot.execute<Message, SendLocation>(SendLocation(location.latitude, location.longitude).setChatId(chatId)).messageId!!
            }
        }
        return messageId
    }

    fun sendFile(fileName: String, stream: InputStream, chatId: Long): Int {
        var messageId = 0
        try {
            messageId = bot.execute(SendDocument()
                    .setDocument(fileName, stream)
                    .setChatId(chatId)
            ).messageId!!
        } catch (e: TelegramApiException) {
            logger.error("Can't send file - {}, cause - {}", fileName, e)
            sendMessage("Ошибка отправки файла", chatId)
        }

        return messageId
    }

    fun getTitle(chatId: Long): String {
        return bot.execute<Chat, GetChat>(GetChat(chatId)).title
    }

    fun getUserName(chatId: Long): String {
        return bot.execute<Chat, GetChat>(GetChat(chatId)).userName
    }

    fun getDescription(chatId: Long): String {
        return bot.execute<Chat, GetChat>(GetChat(chatId)).description
    }

    fun getInviteLink(chatId: Long): String {
        return bot.execute<Chat, GetChat>(GetChat(chatId)).inviteLink
    }

    fun getInlineKeyboard(list: List<InlineKeyboardButton>): InlineKeyboardMarkup {
        val keyboard = InlineKeyboardMarkup()
        val rowsKeyboard = ArrayList<List<InlineKeyboardButton>>()
        list.forEach { b ->
            val row = ArrayList<InlineKeyboardButton>()
            row.add(b)
            rowsKeyboard.add(row)
        }
        keyboard.keyboard = rowsKeyboard
        return keyboard
    }

    fun getFile(fileId: String): File {
        return bot.downloadFile(bot.execute(GetFile().setFileId(fileId)).filePath)
    }


}