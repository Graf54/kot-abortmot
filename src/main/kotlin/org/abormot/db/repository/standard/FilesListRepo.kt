package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.Files
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FilesListRepo : CrudRepository<Files, Int>
