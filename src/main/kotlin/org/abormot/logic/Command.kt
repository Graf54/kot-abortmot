package org.abormot.logic

import org.telegram.telegrambots.meta.api.objects.Update

abstract class Command {
    var isFinish = false
    fun execute(update: Update) {

    }
}
