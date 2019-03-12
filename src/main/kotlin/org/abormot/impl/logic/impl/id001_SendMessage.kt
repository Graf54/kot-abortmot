package org.abormot.impl.logic.impl

import org.abormot.impl.bot.util.UpdateUtil
import org.abormot.impl.logic.Command
import org.telegram.telegrambots.meta.api.objects.Update

class id001_SendMessage : Command() {
    override fun execute(update: Update) {
        botUtils.sendMessage("Hello", UpdateUtil.getChatId(update))
        
    }

}