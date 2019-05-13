package org.abormot.api


interface CommandFactory {
    fun getCommand(id: Int): Command
}