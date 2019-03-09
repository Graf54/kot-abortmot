package org.abormot.bot

import mu.KotlinLogging
import org.abormot.bot.util.UpdateUtil
import org.abormot.logic.Command
import org.abormot.services.CommandService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class UpdateHandlerImpl(val commandService: CommandService) : UpdateHandler {
    private val commandUserMap: MutableMap<Long, Command?> = HashMap()
    private val logger = KotlinLogging.logger {}
    override fun handle(update: Update) {
        logger.info { "Get new ${update.toString()}" }
        val chatId = UpdateUtil.getChatId(update)
        val command = getCommand(update, chatId)
        if (command == null) {

        } else {
            execute(command, update, chatId)
        }
    }

    private fun getCommand(update: Update, chatId: Long): Command? {
        val command = commandService.getCommand(update) // check is new Command
        return if (command != null) { // was created new Command
            commandUserMap[chatId] = command
            command
        } else {
            commandUserMap[chatId]
        }
    }

    private fun execute(command: Command, update: Update, chatId: Long) {
        command.execute(update)
        if (command.isFinish) {
            commandUserMap[chatId] = null
        }
    }
}