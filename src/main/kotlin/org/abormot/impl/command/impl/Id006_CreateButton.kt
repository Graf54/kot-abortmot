package org.abormot.impl.command.impl

import org.abormot.api.Command
import org.abormot.impl.bot.util.BotUtil
import org.abormot.impl.bot.util.UpdateUtil
import org.abormot.impl.db.service.ButtonService
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component("6")
@Scope("prototype")
class Id006_CreateButton(val buttonService: ButtonService, val botUtil: BotUtil) : Command {
    var step: Command = mainMenu()

    override fun execute(update: Update) {
        step.execute(update)
    }

    private fun mainMenu(): Command {
        return object : Command {
            override fun execute(update: Update) {
                val string = """Меню создания меню бота
                    |/help - команда помощи
                    |/add - добавить кнопку
                    |/edit - редактировать кнопку
                    |/addMenu - добавить меню
                    |""".trimMargin()
                botUtil.sendMessage(string, UpdateUtil.getChatId(update))
                step = router()
            }
        }
    }

    private fun router(): Command {
        return object : Command {
            override fun execute(update: Update) {
                val string = update.message.text
                when (true) {
                    string == "/help" -> step = mainMenu()
                    string == "/add" -> step = mainMenu()
                    string == "/addMenu" -> step = mainMenu()
                    string == "/edit" -> step = mainMenu()
                }
                botUtil.sendMessage(string, UpdateUtil.getChatId(update))
                step = router()
            }
        }
    }

}