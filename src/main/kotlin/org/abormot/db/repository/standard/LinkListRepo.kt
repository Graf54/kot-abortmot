package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.LinkList
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkListRepo : CrudRepository<LinkList, Int>
