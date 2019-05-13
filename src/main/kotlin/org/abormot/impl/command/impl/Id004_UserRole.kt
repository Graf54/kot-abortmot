package org.abormot.impl.command.impl

import org.abormot.api.Command
import org.abormot.impl.bot.util.BotUtil
import org.abormot.impl.bot.util.UpdateUtil
import org.abormot.impl.db.service.StatusService
import org.abormot.impl.db.service.UserService
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component("4")
@Scope("prototype")
class Id004_UserRole(val botUtil: BotUtil, val userService: UserService, val statusService: StatusService) : Command {
    override fun execute(update: Update) {
        statusService.getStatus(1)
//        userService.getListStatus(`)
        botUtil.sendMessage("Hello", UpdateUtil.getChatId(update))
    }
}