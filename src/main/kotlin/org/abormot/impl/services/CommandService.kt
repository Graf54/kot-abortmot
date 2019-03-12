package org.abormot.impl.services

import org.abormot.impl.bot.util.UpdateUtil
import org.abormot.impl.logic.Command
import org.abormot.impl.logic.CommandFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class CommandService(private val commandFactory: CommandFactory) {

    fun getCommand(update: Update): Command? {
        var command = ""
        //todo realize later
        when (true) {
            update.hasCallbackQuery() -> command = update.callbackQuery.data
            true -> command = UpdateUtil.getMessage(update).text
        }
        return commandFactory.getCommand(1)
    }
}