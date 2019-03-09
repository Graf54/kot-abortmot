package org.abormot.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource(value = ["file:C:\\kotlin\\kot-abortmot2\\src\\main\\resource\\config.properties"])
class Config {
    @Value("\${bot.proxy.type}")
    var type: String = "NO_PROXY"

    @Value("\${bot.proxy.port}")
    var port: Int = 0

    @Value("\${bot.proxy.host}")
    var host: String = ""

    @Value("\${bot.proxy.login}")
    var login: String = ""

    @Value("\${bot.proxy.pass}")
    var pass: String = ""

    @Value("\${bot.start}")
    var start = ""

    @Value("\${bot.username}")
    var botUsername = ""

    @Value("\${bot.token}")
    var botToken = ""
}