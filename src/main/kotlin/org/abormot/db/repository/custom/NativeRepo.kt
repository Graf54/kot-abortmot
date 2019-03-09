package org.abormot.db.repository.custom

interface NativeRepo {
    fun executeSql(sql: String): List<*>
}
