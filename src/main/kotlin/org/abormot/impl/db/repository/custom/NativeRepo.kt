package org.abormot.impl.db.repository.custom

interface NativeRepo {
    fun executeSql(sql: String): List<*>
}
