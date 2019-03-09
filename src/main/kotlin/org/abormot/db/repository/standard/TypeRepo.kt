package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.Type
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TypeRepo : CrudRepository<Type, Int> {
    @Query(value = "SELECT * FROM STANDARD.TYPE WHERE ID=(SELECT TYPE_ID FROM BUSINESS.INFO_TYPE_BUTTON WHERE BUTTON_ID=?1)", nativeQuery = true)
    fun getTypeByButtonId(buttonId: Int): Type
}
