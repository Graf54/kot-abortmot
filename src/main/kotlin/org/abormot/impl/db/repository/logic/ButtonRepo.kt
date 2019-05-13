package org.abormot.impl.db.repository.logic

import org.abormot.impl.db.entity.logic.Button
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util.Optional
import javax.persistence.QueryHint


@Repository
interface ButtonRepo : CrudRepository<Button, Int> {

    @QueryHints(QueryHint(name="org.hibernate.cacheable", value="true")) //todo проверить работу -> https://stackoverflow.com/questions/49751925/does-spring-data-jpa-has-a-l2-cache-implementation
    fun findByName(name: String): Optional<Button>

    fun findByIdAndName(id: Int, name: String): Boolean

    fun countByName(name: String): Int
}
