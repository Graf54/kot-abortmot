package org.abormot.impl.db.repository.standard

import org.abormot.impl.db.entity.standard.Files
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FilesListRepo : CrudRepository<Files, Int>
