package org.abormot.impl.db.repository.logic

import org.abormot.impl.db.entity.logic.Button
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util.Optional


@Repository
interface ButtonRepo : CrudRepository<Button, Int> {


    fun findByName(name: String): Optional<Button>

    fun findByIdAndName(id: Int, name: String): Boolean

    fun countByName(name: String): Int
}
