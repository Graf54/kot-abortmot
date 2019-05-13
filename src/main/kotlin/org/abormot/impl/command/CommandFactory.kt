package org.abormot.impl.command

import mu.KotlinLogging
import org.abormot.api.Command
import org.abormot.api.CommandFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.stereotype.Service

@Service
class CommandFactory(val beanFactory: BeanFactory) : CommandFactory {
    private val logger = KotlinLogging.logger {}
    override fun getCommand(id: Int): org.abormot.api.Command {
        return beanFactory.getBean(id.toString(), Command::class.java)
    }
}