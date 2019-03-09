package org.abormot

import mu.KotlinLogging
import org.abormot.bot.Config
import org.abormot.bot.KotBot
import org.abormot.bot.Options
import org.abormot.bot.UpdateHandler
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi

@EntityScan(basePackages = ["org.abormot.db.entity.business", "org.abormot.db.entity.logic", "org.abormot.db.entity.standard"])
@SpringBootApplication
class SpringApp(val options: Options, val updateHandler: UpdateHandler, val config: Config) : CommandLineRunner {
    private val logger = KotlinLogging.logger {}
    override fun run(vararg args: String?) {
        val bot = KotBot(options, updateHandler, config)
        ApiContextInitializer.init()
        logger.info { "ApiContextInitializer init normal" }
        TelegramBotsApi().registerBot(bot)
        logger.info { "TelegramBot ${bot.botUsername} was registered by token: ${bot.botToken}" }

    }
}

fun main(args: Array<String>) {
    runApplication<SpringApp>(*args)
}