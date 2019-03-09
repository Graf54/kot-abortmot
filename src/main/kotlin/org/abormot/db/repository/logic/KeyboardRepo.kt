package org.abormot.db.repository.logic

import org.abormot.db.entity.logic.Keyboard
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KeyboardRepo : CrudRepository<Keyboard, Int>
