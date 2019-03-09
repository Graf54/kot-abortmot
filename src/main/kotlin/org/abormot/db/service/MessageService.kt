package org.abormot.db.service

import org.abormot.db.entity.logic.Message
import org.abormot.db.entity.standard.Files
import org.abormot.db.entity.standard.TFile
import org.abormot.db.repository.logic.MessageRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class MessageService(private val messageRepo: MessageRepo, private val tFileService: TFileService) {

    fun addTFile(message: Message, tFile: TFile) {
        tFileService.save(tFile)
        if (message.files == null) {
            val tFiles = ArrayList<TFile>()
            tFiles.add(tFile)
            val files = Files()
            files.tFileList = tFiles
            tFileService.save(files)
            message.files = files
            save(message)
        } else {
            message.files!!.tFileList!!.add(tFile)
            tFileService.save(message.files)
        }
    }

    fun save(message: Message) {
        messageRepo.save(message)
    }

    fun getText(messId: Int): String {
        return getMessage(messId).text
    }

    fun getMessage(id: Int): Message {
        return messageRepo.findById(id).orElse(getMessageNotFound(id))
    }

    private fun getMessageNotFound(id: Int): Message {
        val message = Message()
        message.text = "Сообщение с id=$id в БД отсутствует"
        return message
    }

}