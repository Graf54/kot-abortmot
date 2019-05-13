package org.abormot.impl.command.impl

import org.abormot.api.Command
import org.abormot.impl.Utils.botUtil
import org.abormot.impl.bot.util.UpdateUtil
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component("5")
@Scope("prototype")
class id005_Profile : Command {
    override fun execute(update: Update) {
        botUtil.sendMessage("Hello", UpdateUtil.getChatId(update))
    }

}