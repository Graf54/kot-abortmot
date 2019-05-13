package org.abormot.impl.bot

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultBotOptions
import java.net.Authenticator
import java.net.PasswordAuthentication

@Component
class Options(configBot: ConfigBot) : DefaultBotOptions() {
    private val logger = KotlinLogging.logger {}
    private val type: String = configBot.type
    private val port: Int = configBot.port
    private val host: String = configBot.host

    private val login: String = configBot.login
    private val pass: String = configBot.pass

    init {
        logger.info { "ConfigBot values: type - $type, address - $host:$port, login/pass: $login/$pass" }
        val proxyType = ProxyType.valueOf(type)
        super.setProxyType(proxyType)
        super.setProxyPort(port)
        super.setProxyHost(host)
        if (!login.isEmpty() && !pass.isEmpty()) {
            Authenticator.setDefault(object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(login, pass.toCharArray())
                }
            })
        }
    }
}