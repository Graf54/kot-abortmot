package org.abormot.impl.logic

import org.abormot.impl.Utils
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component()
abstract class Command {
    companion object {
        val botUtils = Utils.botUtils
        val updateUtil = Utils.updateUtil
    }
    var isFinish = false
    open fun execute(update: Update) {

    }
}
