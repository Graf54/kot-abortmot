package org.abormot.impl.bot

import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
@Configuration
class ConfigBot(environment: Environment) {
    //  proxy
    val type: String = environment.getProperty("bot.proxy.type", "NO_PROXY")
    val port: Int = environment.getProperty("bot.proxy.port", "0").toInt()
    val host: String = environment.getProperty("bot.proxy.host", "")
    val login: String = environment.getProperty("bot.proxy.login", "")
    val pass: String = environment.getProperty("bot.proxy.login", "")
    //  console
    val start: String = environment.getProperty("bot.start", "")
    //  bot
    val botUsername: String = environment.getProperty("bot.username", "")
    val botToken: String = environment.getProperty("bot.token", "")
}