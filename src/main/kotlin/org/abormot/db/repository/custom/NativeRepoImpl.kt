package org.abormot.db.repository.custom

import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

open class NativeRepoImpl : NativeRepo {
    @PersistenceContext
    private val em: EntityManager? = null

    @Transactional
    override fun executeSql(sql: String): List<*> {
        return em!!.createNativeQuery(sql).resultList
    }
}
