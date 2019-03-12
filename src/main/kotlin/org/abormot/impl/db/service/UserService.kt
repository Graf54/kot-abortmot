package org.abormot.impl.db.service

import mu.KotlinLogging.logger
import org.abormot.impl.db.entity.standard.User
import org.abormot.impl.db.entity.standard.UserLight
import org.abormot.impl.db.entity.standard.UserStatus
import org.abormot.impl.db.repository.standard.StatusRepo
import org.abormot.impl.db.repository.standard.UserRepo
import org.abormot.impl.db.repository.standard.UserStatusRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(val userRepo: UserRepo, val userStatusRepo: UserStatusRepo, val statusRepo: StatusRepo, val statusService: StatusService) {
    val log = logger{}

    val all: Iterable<User>
        get() = userRepo.findAll()



    fun isAdmin(chatId: Long): Boolean {
        return userStatusRepo.countByUser_ChatIdAndStatus_Id(chatId, 1) > 0
    }

    fun isExistTelegramData(chatId: Long): Boolean {
        return userRepo.existsById(chatId)
    }

    fun isRegistered(chatId: Long): Boolean { // проверяем ли оставил пользователь необходимые данные
        return userRepo.countByChatId(chatId) > 0
    }

    fun getByChatId(chatId: Long): User {
        return userRepo.findByChatId(chatId)
    }

    fun getByStatusId(statusId: Int): List<User> {
        return userRepo.getByStatusId(statusId)
    }

    fun save(user: User) {
        userRepo.save(user)
    }

    fun getAllRegistr(limit: Int, offset: Int): List<UserLight> {
        return userRepo.getAllRegistr(limit, offset)
    }

    fun getAll(limit: Int, offset: Int): List<UserLight> {
        return userRepo.getAll(limit, offset)
    }

    fun getAll(statusId: Int, limit: Int, offset: Int): List<UserLight> {
        return if (statusId == 0) {
            userRepo.getAll(limit, offset)
        } else userRepo.getAll(statusId, limit, offset)
    }

    fun getListStatus(user: User): List<UserStatus> {
        return userStatusRepo.findAllByUser(user)
    }

    fun countRegistersUsers(): Int {
        return userRepo.countByPhoneNotNull()
    }

    fun countUser(): Int {
        return userRepo.count().toInt()
    }

    fun countUser(statusId: Int): Int {
        return if (statusId == 0) {
            countUser()
        } else userStatusRepo.countByStatus_Id(statusId)
    }

    fun isStatusExist(chatId: Long, statusId: Int): Boolean {
        return userStatusRepo.countByUser_ChatIdAndStatus_Id(chatId, statusId) > 0
    }

    fun changeStatus(user: User, statusId: Int) {
        val userStatus = userStatusRepo.findByUserAndStatus_Id(user, statusId)
        if (userStatus.isPresent()) {
            userStatusRepo.delete(userStatus.get())
        } else {
            userStatusRepo.save(UserStatus(0, user, statusRepo.getOne(statusId)))
        }
    }

    @Transactional
    fun deleteStatusFromUsers(statusId: Int) {
        val count = userStatusRepo.deleteByStatus_Id(statusId)
        log.info("deleted statusId={} from {} users", statusId, count)
    }

    fun findByPhone(phone: String): List<User> {
        return userRepo.findTop30ByPhoneLikeIgnoreCaseOrderByChatId(phone)
    }

    fun findByName(updateMessageText: String): List<User> {
        return userRepo.findTop30ByUserNameLikeIgnoreCaseOrderByChatId("%$updateMessageText%")
    }
}