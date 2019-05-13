package org.abormot.impl

import mu.KotlinLogging
import org.abormot.impl.bot.KotBot
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi

@EntityScan(basePackages = ["org.abormot.impl.db.entity.business", "org.abormot.impl.db.entity.logic", "org.abormot.impl.db.entity.standard"])
@SpringBootApplication
@EnableCaching
class SpringApp(val bot: KotBot) : CommandLineRunner {

    private val logger = KotlinLogging.logger {}
    override fun run(vararg args: String?) {
        logger.info { "Start app" }
        ApiContextInitializer.init()
        logger.info { "ApiContextInitializer init normal" }
        TelegramBotsApi().registerBot(bot)
        logger.info { "TelegramBot ${bot.botUsername} was registered by token: ${bot.botToken}" }


    }
}

fun main(args: Array<String>) {
    runApplication<SpringApp>(*args)
}