package org.abormot.impl.services

import org.abormot.api.Command
import org.abormot.api.CommandFactory
import org.abormot.impl.bot.util.UpdateUtil
import org.abormot.impl.db.service.ButtonService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class CommandService(private val commandFactory: CommandFactory, private val buttonService: ButtonService) {

    fun getCommand(update: Update): Command? {
        val text = when (true) {
            update.hasCallbackQuery() -> update.callbackQuery.data
            true -> UpdateUtil.getMessage(update).text
            else -> ""
        }
        if (text.isEmpty()) {
            return null
        }
        val button = buttonService.getButton(text) ?: return null
        if (button.commandId == 0) {
            return null
        }
        return commandFactory.getCommand(button.commandId)
    }
}