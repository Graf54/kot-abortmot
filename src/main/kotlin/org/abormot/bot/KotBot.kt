package org.abormot.bot

import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
@Configuration
class KotBot(options: DefaultBotOptions?, val updateHandler: UpdateHandler, val config: Config) : TelegramLongPollingBot(options) {

    override fun getBotUsername(): String {
        return config.botUsername
    }

    override fun getBotToken(): String {
        return config.botToken
    }

    override fun onUpdateReceived(update: Update?) {
        if (update == null) return
        updateHandler.handle(update)
    }
}