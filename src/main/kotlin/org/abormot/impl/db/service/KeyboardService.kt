package org.abormot.impl.db.service

import mu.KotlinLogging
import org.abormot.impl.db.Const
import org.abormot.impl.db.entity.logic.Keyboard
import org.abormot.impl.db.repository.logic.KeyboardRepo
import org.abormot.impl.db.entity.logic.Button
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.io.UnsupportedEncodingException
import java.util.*

@Service
@Transactional
class KeyboardService {
    private val logger = KotlinLogging.logger {}
    val keyboardRepo: KeyboardRepo
    val buttonService: ButtonService

    constructor(keyboardRepo: KeyboardRepo, buttonService: ButtonService) {
        this.keyboardRepo = keyboardRepo
        this.buttonService = buttonService
    }


    fun isInline(id: Int): Boolean {
        return keyboardRepo.findById(id).get().isInline
    }

    fun select(id: Int): ReplyKeyboard? {
        if (id < 0) {
            return ReplyKeyboardRemove()
        }
        return if (id == 0) {
            null
        } else select(keyboardRepo.findById(id).get())
    }

    fun select(keyboard: Keyboard): ReplyKeyboard? {
        val buttonIds = keyboard.buttonIds ?: return null
        val rows = buttonIds.split(Const.SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (keyboard.isInline) {
            getInlineKeyboard(rows)
        } else {
            getReplyKeyboard(rows)
        }

    }

    private fun getReplyKeyboard(rows: Array<String>): ReplyKeyboard {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        replyKeyboardMarkup.resizeKeyboard = true
        val keyboardRowList = ArrayList<KeyboardRow>()
        var isRequestContact = false
        for (buttonIdsString in rows) {
            val keyboardRow = KeyboardRow()
            val buttonIds = buttonIdsString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (buttonId in buttonIds) {
                val buttonFromDb = buttonService.getButton(Integer.parseInt(buttonId))
                val button = KeyboardButton()
                val buttonText = buttonFromDb.name
                button.text = buttonText
                button.requestContact = buttonFromDb.isRequestContact
                if (buttonFromDb.isRequestContact) {
                    isRequestContact = true
                }
                keyboardRow.add(button)
            }
            keyboardRowList.add(keyboardRow)
        }

        replyKeyboardMarkup.keyboard = keyboardRowList
        replyKeyboardMarkup.oneTimeKeyboard = isRequestContact
        return replyKeyboardMarkup
    }

    private fun getCallback(button: Button): String {
        return try {
            val size = button.name.toByteArray(Charsets.UTF_8).size    // пиздец кончено, java использует utf-16 но idea делает utf-8, поэтому в idea все работает хорошо, а в jar нет. Чтобы работало в jar и нужно добавить utf-8
            if (size > 64) Const.ID_MARK + button.id else button.name
        } catch (e: Exception) {
            Const.ID_MARK + button.id
        }

    }

    private fun getCallback(callback: String): String {
        try {
            val bytes = callback.toByteArray(charset("UTF-8"))
            val length = bytes.size    // пиздец кончено, java использует utf-16 но idea делает utf-8, поэтому в idea все работает хорошо, а в jar нет. Чтобы работало в jar и нужно добавить utf-8
            if (length > 64) {
                logger.info("Callback {} has light > 64 bytes", callback)
                return Arrays.toString(Arrays.copyOf(bytes, 64))
            } else {
                return callback
            }
        } catch (e: UnsupportedEncodingException) {
            return Const.ID_MARK + callback
        }

    }

    private fun getInlineKeyboard(rowIds: Array<String>): InlineKeyboardMarkup {
        val keyboard = InlineKeyboardMarkup()
        val rows = ArrayList<List<InlineKeyboardButton>>()

        for (buttonIdsString in rowIds) {
            val row = ArrayList<InlineKeyboardButton>()
            val buttonIds = buttonIdsString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (buttonId in buttonIds) {
                val buttonFromDb = buttonService.getButton(Integer.parseInt(buttonId))
                val button = InlineKeyboardButton()
                val buttonText = buttonFromDb.name
                button.text = buttonText
                val url = buttonFromDb.url
                if (url != null) {
                    button.url = url
                } else {
                    button.callbackData = getCallback(buttonFromDb)
                }
                row.add(button)
            }
            rows.add(row)
        }

        keyboard.keyboard = rows
        return keyboard
    }

    fun getInlineForEdit(buttons: List<String>?): InlineKeyboardMarkup? {
        if (buttons == null || buttons.size == 0) {
            return null
        }
        val keyboard = InlineKeyboardMarkup()
        val rows = ArrayList<List<InlineKeyboardButton>>()
        for (i in buttons.indices) {
            val button = buttons[i]
            val row = ArrayList<InlineKeyboardButton>()
            row.add(InlineKeyboardButton(button).setCallbackData(i.toString()))
            rows.add(row)
        }
        keyboard.keyboard = rows
        return keyboard
    }

    fun addRow(keyboardMarkup: InlineKeyboardMarkup, vararg buttons: String) {
        //todo
        /*InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(buttons).setCallbackData(getCallback())
        keyboardMarkup.getKeyboard().add()*/
    }

    fun getButton(name: String, callback: String): InlineKeyboardButton {
        return InlineKeyboardButton(name).setCallbackData(callback)
    }

    fun getRow(vararg inlineKeyboardButton: InlineKeyboardButton): List<InlineKeyboardButton> {
        return getRow(Arrays.asList(*inlineKeyboardButton))
    }

    fun getRow(inlineKeyboardButton: List<InlineKeyboardButton>): List<InlineKeyboardButton> {
        val row = ArrayList<InlineKeyboardButton>()
        row.addAll(inlineKeyboardButton)
        return row
    }
}
