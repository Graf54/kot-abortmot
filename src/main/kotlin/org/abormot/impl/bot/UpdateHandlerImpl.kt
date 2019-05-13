package org.abormot.impl.bot

import mu.KotlinLogging
import org.abormot.api.Command
import org.abormot.impl.bot.util.BotUtil
import org.abormot.impl.bot.util.UpdateUtil
import org.abormot.impl.db.entity.standard.User
import org.abormot.impl.db.service.UserService
import org.abormot.impl.services.CommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.*

@Service
class UpdateHandlerImpl(val commandService: CommandService, val userService: UserService) {
    @Autowired
    @Lazy
    var botUtil: BotUtil? = null

    private val commandUserMap: MutableMap<Long, Command?> = HashMap()
    private val logger = KotlinLogging.logger {}

    fun handle(update: Update) {
        val chatId = UpdateUtil.getChatId(update)
        checkUser(update, chatId)
        logger.info("Get new - {}", UpdateUtil.updateToString(update))
        val command = getCommand(update, chatId)
        if (command == null) {

            botUtil!!.sendMessage("Команда не найдена", chatId)
        } else {
            execute(command, update, chatId)
        }
    }


    private fun checkUser(update: Update, chatId: Long) {
        if (!userService.isRegistered(chatId)) {
            userService.save(getUser(update))
        }
    }

    private fun getUser(update: Update): User {
        val user = User()
        val tUser = UpdateUtil.getUser(update)
        user.chatId = tUser.id.toLong()
        user.firstName = tUser.firstName
        user.lastName = tUser.lastName
        user.userName = tUser.userName
        user.languageCode = tUser.languageCode
        user.contactBot = Date()
        return user
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
        /*if (command.isFinish) {
            commandUserMap[chatId] = null
        }*/
    }
}