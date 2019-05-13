package org.abormot.impl.command.impl

import org.abormot.api.Command
import org.abormot.impl.bot.util.BotUtil
import org.abormot.impl.bot.util.UpdateUtil
import org.abormot.impl.db.service.ButtonService
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component("1")
@Scope("prototype")
class Id001_SendMessage(val buttonService: ButtonService, val botUtil: BotUtil) : Command {

    override fun execute(update: Update) {
        val button = buttonService.getButton(update)
        botUtil.sendMessage(button!!.message!!.id, UpdateUtil.getChatId(update))
    }

}