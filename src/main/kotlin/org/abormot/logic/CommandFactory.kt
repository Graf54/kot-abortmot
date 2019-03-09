package org.abormot.logic

import org.abormot.logic.impl.id001_SendMessage
import org.springframework.stereotype.Service

@Service
class CommandFactory {
    fun getCommand(id: Int): Command {
        return id001_SendMessage()
    }
}