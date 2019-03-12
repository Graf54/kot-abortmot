package org.abormot.impl.db.repository.logic

import org.abormot.impl.db.entity.logic.Keyboard
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KeyboardRepo : CrudRepository<Keyboard, Int>
