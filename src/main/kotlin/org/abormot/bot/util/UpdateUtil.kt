package org.abormot.bot.util

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User

@Service
class UpdateUtil {
    companion object {
        fun getMessage(update: Update): Message {
            return when (true) {
                update.hasMessage() -> update.message
                update.hasCallbackQuery() -> update.callbackQuery.message
                update.hasEditedMessage() -> update.editedMessage
                update.hasChannelPost() -> update.channelPost
                update.hasEditedChannelPost() -> update.editedChannelPost
                else -> update.message   //todo replace

            }
        }

        fun getChatId(update: Update): Long {
            return when (true) {
                update.hasMessage() -> update.message.chatId
                update.hasCallbackQuery() -> update.callbackQuery.message.chatId
                update.hasInlineQuery() -> update.inlineQuery.from.id.toLong()
                update.hasChosenInlineQuery() -> update.chosenInlineQuery.from.id.toLong()
                update.hasChannelPost() -> update.channelPost.chatId
                update.hasEditedChannelPost() -> update.editedChannelPost.chatId
                update.hasPreCheckoutQuery() -> update.preCheckoutQuery.from.id.toLong()
                update.hasShippingQuery() -> update.shippingQuery.from.id.toLong()
                else -> update.message.chatId //todo replace
            }
        }

        fun getUser(update: Update): User {
            return when (true) {
                update.hasMessage() -> update.message.from
                update.hasEditedMessage() -> update.editedMessage.from
                update.hasCallbackQuery() -> update.callbackQuery.from
                update.hasInlineQuery() -> update.inlineQuery.from
                update.hasShippingQuery() -> update.shippingQuery.from
                update.hasPreCheckoutQuery() -> update.preCheckoutQuery.from
                update.hasChannelPost() -> update.channelPost.from
                update.hasEditedChannelPost() -> update.editedChannelPost.from
                update.hasChosenInlineQuery() -> update.chosenInlineQuery.from
                else -> update.message.from //todo replace

            }
        }

        fun updateToString(update: Update): String {
            return convertString(update, true)
        }

        private fun convertString(update: Update, isShort: Boolean): String {
            var text = update.toString()
            var countTab = 0
            var split = text.split(",")
            val result = StringBuilder()
            result.append("\n")
            val concat = ","
            for (s in split) {
                if (isShort) {
                    if (!(s.contains("=null") || s.contains("='null'"))) {
                        result.append(s).append(concat)
                    }
                } else {
                    result.append(s).append(concat)
                }
                if (s.contains("{")) {
                    countTab++
                } else if (s.contains("}")) {
                    countTab--
                }
            }
            return result.toString()
        }
    }
}