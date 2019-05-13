package org.abormot.impl.command.impl

import org.abormot.api.Command
import org.abormot.impl.bot.util.BotUtil
import org.abormot.impl.bot.util.UpdateUtil
import org.abormot.impl.db.service.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component("2")
@Scope("prototype")
class Id002_SendMessageForAdmin(val userService: UserService, @Qualifier("1") val command: Command, val botUtil: BotUtil) : Command {

    override fun execute(update: Update) {
        if (userService.isAdmin(UpdateUtil.getChatId(update))) {
            command.execute(update)
        } else {
            botUtil.sendMessage("Нет доступа", UpdateUtil.getChatId(update))
        }
    }
}