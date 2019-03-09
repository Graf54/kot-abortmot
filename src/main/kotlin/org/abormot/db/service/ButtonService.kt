package org.abormot.db.service

import org.abormot.db.Const
import org.abormot.db.entity.logic.Button
import org.abormot.db.repository.logic.ButtonRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.objects.Update

@Service
@Transactional
class ButtonService(val buttonRepo: ButtonRepo) {

    fun getButton(buttonId: Int): Button {
        return buttonRepo.findById(buttonId).get()
    }

    fun save(button: Button) {
        buttonRepo.save(button)
    }

    fun isButton(id: Int, buttonName: String): Boolean {
        return buttonRepo.findByIdAndName(id, buttonName)
    }

    fun getButtonId(updateMessageText: String): Int {
        return buttonRepo.findByName(updateMessageText).get().id
    }

    fun isExist(buttonName: String): Boolean {
        return buttonRepo.countByName(buttonName) > 0
    }

    fun getButton(updateMessageText: String): Button {
        return getButton(updateMessageText)
    }

    fun getButton(update: Update): Button? {
        var updateMessage = update.message
        var inputtedText: String?
        if (update.hasCallbackQuery()) {
            inputtedText = update.callbackQuery.data.split(Const.SPLIT.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0]
            updateMessage = update.callbackQuery.message
            try {
                if (inputtedText != null && inputtedText!!.substring(0, 3) == Const.ID_MARK) {
                    try {
                        val id = Integer.parseInt(inputtedText!!.split(Const.SPLIT.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0].replace("/id".toRegex(), ""))
                        //                        inputtedText = ProviderRepo.getButtonRepo().findByButtonIdAndLang(id, LangService.getCurrentLang()).getName();
                    } catch (e: Exception) {
                        inputtedText = updateMessage.text
                    }

                }
            } catch (e: Exception) {
                //e.printStackTrace();
            }

        } else {
            inputtedText = updateMessage.text
        }
        return inputtedText?.let { getButton(it) }
    }
}
