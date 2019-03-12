package org.abormot.api.bot

import org.telegram.telegrambots.meta.api.objects.Update

interface Bot {
    fun getBotUsername(): String
    fun getBotToken(): String
    fun onUpdateReceived(update: Update?)
}