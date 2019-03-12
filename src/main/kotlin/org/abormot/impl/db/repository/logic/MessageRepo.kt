package org.abormot.impl.db.repository.logic

import org.abormot.impl.db.entity.logic.Message
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepo : CrudRepository<Message, Int>
