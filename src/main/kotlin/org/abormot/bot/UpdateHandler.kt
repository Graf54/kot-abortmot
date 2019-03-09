package org.abormot.bot

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateHandler {
    fun handle(update: Update)
}