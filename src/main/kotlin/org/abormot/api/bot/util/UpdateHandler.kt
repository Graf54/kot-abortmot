package org.abormot.api.bot.util

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateHandler {
    fun hanlde(update: Update)
}