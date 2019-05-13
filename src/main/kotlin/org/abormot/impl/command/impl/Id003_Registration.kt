package org.abormot.impl.command.impl

import org.abormot.api.Command
import org.abormot.impl.bot.util.BotUtil
import org.abormot.impl.bot.util.UpdateUtil
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component("3")
@Scope("prototype")
class Id003_Registration(val botUtil: BotUtil) : Command {
    override fun execute(update: Update) {
        botUtil.sendMessage("Registration not realized", UpdateUtil.getChatId(update))
    }

}